package game.auth.tictactoe.domain.service.auth.impl;

import game.auth.tictactoe.domain.model.User;
import game.auth.tictactoe.domain.service.auth.AuthService;
import game.auth.tictactoe.domain.service.user.UserService;
import game.auth.tictactoe.exception.InvalidCredentialsException;
import game.auth.tictactoe.exception.MissingAuthorizationException;
import game.auth.tictactoe.exception.UsernameAlreadyExistsException;
import game.auth.tictactoe.web.model.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(SignUpRequest signUpRequest) {
        if(userService.findByUsername(signUpRequest.username()).isPresent()) {
            log.error("User already exists: {}", signUpRequest.username());
            throw new UsernameAlreadyExistsException("User already exists: " + signUpRequest.username());
        }

        userService.createUser(
                signUpRequest.username(),
                passwordEncoder.encode(signUpRequest.password())
        );
    }

    @Override
    public UUID authenticate(String authorizationHeader) {
        validateAuthorizationHeader(authorizationHeader);

        final String decodedCredentials = decodeCredentials(authorizationHeader);

        final String[] credentials = extractUsernameAndPassword(decodedCredentials);

        final String username = credentials[0];
        final String password = credentials[1];

        return validateUser(username, password, authorizationHeader);
    }

    private void validateAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader == null)
            throw new MissingAuthorizationException("Authorization header is missing");
        else if (!authorizationHeader.startsWith("Basic "))
            throw new InvalidCredentialsException("Authorization header is missing or does not contain Basic authentication");

    }

    private String decodeCredentials(String authorizationHeader) {
        final String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();

        try {
            return new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw new InvalidCredentialsException("Credentials not in base64 " + authorizationHeader);
        }
    }

    private String[] extractUsernameAndPassword(String decodedCredentials) {
        final String[] credentials = decodedCredentials.split(":", 2);

        Arrays.stream(credentials).forEach(s -> log.info("Credentials {}", s));

        if (credentials.length != 2)
            throw new InvalidCredentialsException("Invalid credentials format");

        return credentials;
    }

    private UUID validateUser(String username, String password, String authorizationHeader) {
        final Optional<User> user = userService.findByUsername(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get().getId();
        } else {
            throw new InvalidCredentialsException("Unauthorized " + authorizationHeader);
        }
    }
}
