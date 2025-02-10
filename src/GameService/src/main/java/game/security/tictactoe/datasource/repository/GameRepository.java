package game.security.tictactoe.datasource.repository;

import game.security.tictactoe.domain.model.game.Game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository {

    /**
     * Saves the given {@link Game} entity to the repository.
     *
     * @param game The game entity to be saved.
     * @return The saved game entity.
     */
    Game save(final Game game);

    Game update(final Game game);

    /**
     * Deletes the {@link Game} with the given {@link UUID} from the repository.
     *
     * @param uuid The UUID of the {@link Game} to be deleted.
     */
    void deleteByUuid(final UUID uuid);

    /**
     * Finds a {@link Game} by its {@link UUID} in the repository.
     *
     * @param uuid The {@link UUID} of the {@link Game} to find.
     * @return An {@link Optional} containing the {@link Game} if found, otherwise an empty {@link Optional}.
     */
    Optional<Game> findByUuid(final UUID uuid);

    List<Game> findAll();
}
