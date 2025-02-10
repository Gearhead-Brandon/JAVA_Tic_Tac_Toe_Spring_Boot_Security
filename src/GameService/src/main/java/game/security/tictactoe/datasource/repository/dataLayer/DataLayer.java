package game.security.tictactoe.datasource.repository.dataLayer;

import game.security.tictactoe.datasource.model.GameModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DataLayer extends CrudRepository<GameModel, UUID> {
    Optional<GameModel> findByUuid(UUID uuid);
}
