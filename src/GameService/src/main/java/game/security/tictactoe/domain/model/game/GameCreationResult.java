package game.security.tictactoe.domain.model.game;

import game.security.tictactoe.domain.model.MoveResult;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Represents the result of creating a new game.
 *
 * <p> This class is used to pass information about the game created and the result of the first move.
 *
 * @param uuid Unique identifier of the game created.
 * @param moveResult The result of the first move.
 */
public record GameCreationResult(@NotNull UUID uuid, @NotNull MoveResult moveResult) {}
