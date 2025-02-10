package game.security.tictactoe.domain.service.game;

import game.security.tictactoe.domain.model.game.Game;
import game.security.tictactoe.domain.model.game.GameCreationResult;
import game.security.tictactoe.domain.model.game.GameField;
import game.security.tictactoe.domain.model.MoveResult;
import game.security.tictactoe.web.model.AvailableGameDTO;
import game.security.tictactoe.web.model.GameCreationRequest;
import game.security.tictactoe.web.model.GameDTO;
import game.security.tictactoe.web.model.GameFieldDTO;
import jdk.dynalink.linker.LinkerServices;

import java.util.List;
import java.util.UUID;

/**
 * This interface defines the core operations for managing a game.
 *
 * <p> It provides methods for creating new games, validating game states,
 * processing player moves, and checking for win conditions.
 */
public interface GameService {
//    /**
//     * Creates a new game, assigns the specified player side,
//     * and makes the initial move for the game.
//     *
//     * @param playerSide The player's side (X or O).
//     * @param gameField The initial state of the game field.
//     * @return A {@link GameCreationResult} object containing the game's {@link UUID} and the result of the initial move.
//     */
    GameCreationResult createGame(final UUID userId, final GameCreationRequest request);

    GameDTO getGame(final UUID uuid);

    List<AvailableGameDTO> getAvailableGames(UUID userId);

    void joinGame(final UUID gameUuid, final UUID userId);

    MoveResult updateGame(final UUID uuid, final GameFieldDTO gameDTO, final UUID userId);

//    /**
//     * Checks if the given game board matches the previous game state.
//     *
//     * @param uuid The UUID of the current game.
//     * @param field The new field to check.
//     * @return the Game object if the field is valid.
//     */
//    Game validateGameField(final UUID uuid, final GameField field);
//
//    /**
//     * Processes the next move in the game.
//     *
//     * <p> This method should update the game state based on the player's move.
//     *
//     * @param game The current state of the game.
//     */
//    void nextMove(final Game game);
//
//    /**
//     * Checks for a win condition in the game.
//     *
//     * @param game The current state of the game.
//     * @return A {@link MoveResult} object indicating the outcome of the game:
//     */
//    MoveResult checkWin(final Game game);
}
