package game.security.tictactoe.domain.factory;

import game.security.tictactoe.domain.model.game.CellType;
import game.security.tictactoe.domain.model.game.Game;
import game.security.tictactoe.domain.model.game.GameField;
import game.security.tictactoe.domain.model.player.Player;
import game.security.tictactoe.domain.model.player.move.MoveStrategyType;
import game.security.tictactoe.domain.model.state.GameState;
import game.security.tictactoe.domain.model.state.GameStateType;

import java.util.UUID;

public class GameFactory {

    private GameFactory() {}

    public static Game createGame(UUID playerUuid, CellType playerSide, GameField gameField) {

        final Player firstPlayer = new Player(
                playerUuid,
                playerSide,
                MoveStrategyFactory.createMoveStrategy(MoveStrategyType.DEFAULT)
        );

        final Game game = new Game(
                UUID.randomUUID(),
                gameField,
                firstPlayer,
                null,
                null
        );

        final GameState gameState = GameStateFactory.createGameState(
                GameStateType.WAITING_FOR_PLAYER, game,null
        );

        game.setState(gameState);

        return game;
    }
}
