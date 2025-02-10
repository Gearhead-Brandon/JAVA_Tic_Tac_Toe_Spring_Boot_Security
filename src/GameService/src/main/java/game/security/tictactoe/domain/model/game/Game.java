package game.security.tictactoe.domain.model.game;

import game.security.tictactoe.domain.model.player.Player;
import game.security.tictactoe.domain.model.state.GameState;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

/**
 * Represents a single game of Tic-Tac-Toe.
 *
 * <p> This class encapsulates the game's state, including its unique identifier,
 * the player's assigned side (X or O), and the game board itself.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Game {

    private final UUID uuid;

    private final GameField gameField;

    private Player firstPlayer;

    @Setter
    private Player secondPlayer;

    @Setter
    private GameState state;

    public void addPlayer(@NotNull Player player){
        if (secondPlayer == null)
            secondPlayer = player;
    }

    public Player getPlayer(UUID playerUuid) {
        if (firstPlayer.getId().equals(playerUuid))
            return firstPlayer;
        else if (secondPlayer != null && secondPlayer.getId().equals(playerUuid))
            return secondPlayer;

        return null;
    }
}
