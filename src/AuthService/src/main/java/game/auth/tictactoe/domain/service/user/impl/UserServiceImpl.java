package game.auth.tictactoe.domain.service.user.impl;

import game.auth.tictactoe.domain.model.User;
import game.auth.tictactoe.domain.repository.UserRepository;
import game.auth.tictactoe.domain.service.user.UserService;
import game.auth.tictactoe.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserDto> findByUuid(UUID id) {
        return userRepository.findById(id).map(value -> new UserDto(value.getUsername(), value.getPassword()));
    }

    @Override
    public void createUser(String username, String password){
        userRepository.save(
                new User(
                        UUID.randomUUID(),
                        username,
                        password,
                        "ROLE_USER"
                )
        );
    }


}
