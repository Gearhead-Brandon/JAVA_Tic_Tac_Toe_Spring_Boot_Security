package game.auth.tictactoe.domain.service.auth;

import game.auth.tictactoe.web.model.SignUpRequest;

import java.util.UUID;

public interface AuthService {
    void signup(SignUpRequest signUpRequest);
    UUID authenticate(String authorizationHeader);
}
