package game.auth.tictactoe.domain.repository;

import game.auth.tictactoe.domain.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUuid(UUID uuid);
}
