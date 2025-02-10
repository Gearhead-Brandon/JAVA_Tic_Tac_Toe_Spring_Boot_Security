package game.security.tictactoe.datasource.mapper;

import game.security.tictactoe.datasource.model.GameFieldModel;
import game.security.tictactoe.datasource.model.GameModel;
import game.security.tictactoe.datasource.model.GameStateModel;
import game.security.tictactoe.domain.factory.GameStateFactory;
import game.security.tictactoe.domain.model.game.CellType;
import game.security.tictactoe.domain.model.game.Game;
import game.security.tictactoe.domain.model.game.GameField;
import game.security.tictactoe.domain.model.state.GameState;
import game.security.tictactoe.domain.service.GameAttribute;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper class that provides the conversion between {@link Game} and {@link GameModel} objects.
 *
 * <p> Uses the MapStruct library to automatically generate the conversion code.
 * The {@link Mapper} annotation specifies that this class is a mapper, and the componentModel="spring" parameter
 * tells MapStruct to create a Spring bean for this mapper.
 */
@Mapper(componentModel = "spring", uses = {PlayerMapper.class, GameStateMapper.class})
public interface GameMapper {

    PlayerMapper playerMapper = new PlayerMapperImpl();
    GameStateMapper gameStateMapper = new GameStateMapperImpl();

    /**
     * Maps a {@link Game} entity to a {@link GameModel}.
     *
     * @param game The {@link Game} entity to be mapped.
     * @return The corresponding {@link GameModel}.
     */
    default GameModel toModel(final Game game){
        if(game == null) return null;

        final GameStateModel gameStateModel = gameStateMapper.toModel(game.getState());

        final GameModel gameModel = new GameModel(
                game.getUuid(),
                playerMapper.toModel(game.getFirstPlayer()),
                playerMapper.toModel(game.getSecondPlayer()),
                gameStateModel
        );

        final GameField gameField = game.getGameField();
        final GameFieldModel gameFieldModel = gameModel.getGameField();

        for(int i = 0; i < GameAttribute.ROWS.getValue(); i++)
            for(int j = 0; j < GameAttribute.COLS.getValue(); j++)
                gameFieldModel.setCell(i, j, gameField.getCell(i, j).getValue());

        return gameModel;
    }

    /**
     * Maps a {@link GameModel} to a {@link Game} entity.
     *
     * @param gameModel The {@link GameModel} to be mapped.
     * @return The corresponding {@link Game} entity.
     */
    default Game toEntity(final GameModel gameModel){
        if(gameModel == null) return null;

        final GameField gameField = new GameField();
        final GameFieldModel gameFieldModel = gameModel.getGameField();

        for(int i = 0; i < GameAttribute.ROWS.getValue(); i++)
            for(int j = 0; j < GameAttribute.COLS.getValue(); j++)
                gameField.setCell(i, j, CellType.valueOf(gameFieldModel.getCell(i, j)));

        final Game game = new Game(
                gameModel.getUuid(),
                gameField,
                playerMapper.toEntity(gameModel.getFirstPlayer()),
                playerMapper.toEntity(gameModel.getSecondPlayer()),
                null
        );

        final GameStateModel gameStateModel = gameModel.getGameState();
        final GameState gameStateType = GameStateFactory.createGameState(
                gameStateModel.getState(),
                game,
                gameStateModel.getCurrentPlayerUuid()
        );

        game.setState(gameStateType);

        return game;
    }

    List<Game> toEntities(Iterable<GameModel> gameModels);
}
