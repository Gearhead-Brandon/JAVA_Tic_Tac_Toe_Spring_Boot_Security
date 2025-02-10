package game.auth.tictactoe.domain.service.user;

import game.auth.tictactoe.domain.model.User;
import game.auth.tictactoe.web.model.UserDto;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> findByUsername(String username);
    Optional<UserDto> findByUuid(UUID id);
    void createUser(String username, String password);
}
