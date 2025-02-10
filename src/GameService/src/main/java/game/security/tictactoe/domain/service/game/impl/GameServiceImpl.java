package game.security.tictactoe.domain.service.game.impl;

import game.security.tictactoe.datasource.repository.GameRepository;
import game.security.tictactoe.domain.factory.GameFactory;
import game.security.tictactoe.domain.model.game.*;
import game.security.tictactoe.domain.model.player.Player;
import game.security.tictactoe.domain.model.state.GameStateType;
import game.security.tictactoe.domain.service.auth.AuthClient;
import game.security.tictactoe.domain.service.game.GameService;
import game.security.tictactoe.domain.model.*;
import game.security.tictactoe.exception.InvalidOperationException;
import game.security.tictactoe.exception.InvalidRequestBodyException;
import game.security.tictactoe.exception.ResourceNotFoundException;
import game.security.tictactoe.web.mapper.GameFieldMapper;
import game.security.tictactoe.web.model.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final GameFieldMapper gameFieldMapper;
    private final AuthClient authClient;
    private static final String absent = "absent";

    @Override
    @Transactional
    public GameCreationResult createGame(@NotNull final UUID userId, @NotNull final GameCreationRequest request) {
        final CellType playerSide = CellType.valueOf(request.getPlayerSide());
        final GameField gameField = gameFieldMapper.toEntity(request.getGameField());

        initialGameFieldValidation(playerSide, gameField);

        Game game = GameFactory.createGame(userId, playerSide, gameField);

        if(request.getBattleType() == GameType.PVE){
            game.getState().onPlayerJoined(Player.COMPUTER_UUID);
            log.info("<<< PVE >>>");
            game.getState().onMove(null, Player.COMPUTER_UUID);
        }

        log.info("Game created: {}", game);

        gameRepository.save(game);

        return new GameCreationResult(game.getUuid(), new MoveResult(game.getState().getStatusMessage(), game.getGameField()));
    }

    /**
     * Performs initial validation on the provided game field.
     *
     * <p> This method checks if the initial game field is valid based on the following rules:
     * <ul>
     * <li> Only the player's side or empty cells are allowed in the initial field. </li>
     * <li> If the player's side is 'O', there should be no 'O' cells in the initial field. </li>
     * <li> If the player's side is 'X', there should be exactly one 'X' cell in the initial field. </li>
     * </ul>
     *
     * @param playerSide The player's side (X or O).
     * @param gameField The initial state of the game field.
     */
    private void initialGameFieldValidation(final CellType playerSide, final GameField gameField) {
        int counter = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final CellType cellType = gameField.getCell(i, j);
                if (cellType != CellType.EMPTY && (cellType != playerSide || counter++ > 1))
                    throw new InvalidRequestBodyException("Invalid game field, it should be matrix with 3 rows and 3 columns of X or O or empty cells");
            }
        }

        if(playerSide == CellType.O && counter > 0)
            throw new InvalidRequestBodyException("With player O the field must be empty");

        if(playerSide == CellType.X && counter != 1)
            throw new InvalidRequestBodyException("With player X the field must have one X");
    }

    @Override
    public GameDTO getGame(UUID uuid) {
        final Game game = getGameByUuid(uuid); // Извлекаем игру по UUID

        final UserDTO firstPlayer = authClient.getUser(game.getFirstPlayer().getId()); // Получаем первого игрока
        final String firstPlayerName = firstPlayer.username();
        final String firstPlayerId = game.getFirstPlayer().getId().toString();
        final String firstPlayerSide = game.getFirstPlayer().getSide().toString();

        final String secondPlayerName = getSecondPlayerName(game.getSecondPlayer()); // Имя второго игрока
        final String secondPlayerId = getSecondPlayerUuid(game.getSecondPlayer());
        final String secondPlayerSide = getPlayerSide(game.getSecondPlayer()); // Сторона второго игрока

        final GameDTO gameDTO = new GameDTO(
                game.getUuid(),
                firstPlayerName,
                firstPlayerId,
                firstPlayerSide,
                secondPlayerName,
                secondPlayerId,
                secondPlayerSide
        );

        setGameField(gameDTO, game.getGameField()); // Заполняем поля игры

        return gameDTO;
    }

    @Override
    public List<AvailableGameDTO> getAvailableGames(UUID userId) {
        return gameRepository.findAll().stream()
                .filter(game -> isGameAvailable(game, userId))
                .map(game -> {
                    final CellType firstPlayerSide = game.getFirstPlayer().getSide();
                    final String secondPlayerSide = firstPlayerSide == CellType.X ? "O" : "X";

                    return new AvailableGameDTO(
                            game.getUuid(),
                            secondPlayerSide
                    );
                })
                .toList();
    }

    public boolean isGameAvailable(Game game, UUID userId) {
        return game.getState().getGameStateType() == GameStateType.WAITING_FOR_PLAYER &&
                !game.getFirstPlayer().getId().equals(userId);
    }

    private Game getGameByUuid(UUID uuid) {
//        Optional<Game> game = gameRepository.findByUuid(uuid);
//        if(game.isPresent())
//            log.info("Game found: {}", game.get());
//        else
//            log.info("||| GAME NOT FOUND |||");
//
//        log.info("UUID: ^^{}^^", uuid);

        return gameRepository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Game not found"));
    }

    private String getSecondPlayerName(Player player) {
        if (player != null) {
            if (player.getId().equals(Player.COMPUTER_UUID)) {
                return "Computer";
            } else {
                return authClient.getUser(player.getId()).username();
            }
        } else {
            return absent;
        }
    }

    private String getSecondPlayerUuid(Player secondPlayer) {
        if(secondPlayer == null)
            return absent;

        return (secondPlayer.getId() == Player.COMPUTER_UUID) ? "COMPUTER" : secondPlayer.getId().toString();
    }

    private String getPlayerSide(Player player) {
        return player == null ? absent : player.getSide().toString();
    }

    private void setGameField(GameDTO gameDTO, GameField gameField) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                gameDTO.setCell(i, j, gameField.getCell(i, j).getValue());
    }

    @Override
    public void joinGame(UUID gameUuid, UUID userId) {
        final Game game = getGameByUuid(gameUuid);

        if(game.getState().getGameStateType() != GameStateType.WAITING_FOR_PLAYER)
            throw new InvalidOperationException("The game is already in progress");

        game.getState().onPlayerJoined(userId);

        gameRepository.update(game);
    }

    @Override
    public MoveResult updateGame(UUID uuid, GameFieldDTO gameDTO, UUID userId) {
        final Game game = getGameByUuid(uuid);

        game.getState().onMove(gameFieldMapper.toEntity(gameDTO), userId);

        if(game.getState().getGameStateType().equals(GameStateType.COMPUTER_TURN)) {
            game.getState().onMove(null, Player.COMPUTER_UUID);
        }

        final GameStateType gameStateType = game.getState().getGameStateType();

        if(gameStateType.equals(GameStateType.PLAYER_WON) || gameStateType.equals(GameStateType.DRAW))
            gameRepository.deleteByUuid(uuid);
        else
            gameRepository.update(game);

        return new MoveResult(game.getState().getStatusMessage(), game.getGameField());
    }
}



//    public Game validateGameField(@NotNull final UUID uuid, @NotNull final GameField newField) {
////        final Game repGame = gameRepository.findByUuid(uuid)
////                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));
////
////        validateFieldChanges(repGame, newField);
////
////        mergeField(repGame.getGameField(), newField);
////
////        return repGame;
//
//        return null;
//    }

//    /**
//     * Validates the changes made to the game field.
//     *
//     * <p> This method checks if the changes made to the game field are valid
//     * according to the game rules.
//     *
//     * @param game The current game instance.
//     * @param newField The updated game field.
//     * @throws InvalidRequestBodyException If the changes to the game field are invalid.
//     */
//    private void validateFieldChanges(final Game game, final GameField newField) {
//        final CellType playerSide = game.getPlayerSide();
//        final GameField oldField = game.getGameField();
//        int numberOfChangedCells = 0;
//
//        for(int i = 0; i < GameAttribute.ROWS.getValue(); i++) {
//            for(int j = 0; j < GameAttribute.COLS.getValue(); j++) {
//
//                final CellType cellType = oldField.getCell(i, j);
//                final CellType newCellType = newField.getCell(i, j);
//
//                if(cellType != newCellType) {
//                    numberOfChangedCells++;
//                    if(newCellType != playerSide || numberOfChangedCells > 1)
//                        throw new InvalidRequestBodyException("Invalid game field");
//                }
//            }
//        }
//
//        if(numberOfChangedCells == 0)
//            throw new InvalidRequestBodyException("Invalid game field");
//    }

//    /**
//     * Merges the changes from the new game field into the old game field.
//     *
//     * <p> This method updates the old game field with the changes from the new game field.
//     *
//     * @param oldField The original {@link GameField}.
//     * @param newField The updated {@link GameField}.
//     */
//    private void mergeField(final GameField oldField, final GameField newField) {
//        for(int i = 0; i < GameAttribute.ROWS.getValue(); i++) {
//            for(int j = 0; j < GameAttribute.COLS.getValue(); j++) {
//                final CellType oldFieldCell = oldField.getCell(i, j);
//                final CellType newFieldCell = newField.getCell(i, j);
//
//                if(oldFieldCell != newFieldCell)
//                    oldField.setCell(i, j, newFieldCell);
//            }
//        }
//    }


//    public void nextMove(@NotNull final Game game) {
////        final CellType side = game.getPlayerSide() == CellType.X ? CellType.O : CellType.X;
////
////        final Position p = minimaxService.findBestMove(game.getGameField(), side);
////
////        if(p.row() != Position.NOT_VALID_POS && p.col() != Position.NOT_VALID_POS)
////            game.getGameField().setCell(p.row(), p.col(), side);
////
////        gameRepository.save(game);
//    }
//
//
//    @Transactional
//    public MoveResult checkWin(@NotNull final Game game) {
////        final WinState winState = GameUtil.isGameOver(game.getGameField());
////
////        if(winState == WinState.X_WON || winState == WinState.O_WON || winState == WinState.DRAW)
////            gameRepository.deleteByUuid(game.getUuid());
////
////        return new MoveResult(winState.name(), game.getGameField());
//
//        return null;
//    }
