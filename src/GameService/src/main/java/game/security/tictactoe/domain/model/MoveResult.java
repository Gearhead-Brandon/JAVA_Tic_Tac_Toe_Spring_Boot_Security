package game.security.tictactoe.domain.model;

import game.security.tictactoe.domain.model.game.GameField;

/**
 * Represents the result of a move in the game.
 *
 * <p> This class encapsulates the status of a move and the updated game field.
 *
 * @param status The textual representation of the move status.
 * @param gameField The updated game field after the move.
 */
public record MoveResult(String status, GameField gameField) {}
