package game.auth.tictactoe.web.controller;

import game.auth.tictactoe.domain.service.auth.AuthService;
import game.auth.tictactoe.domain.service.user.UserService;
import game.auth.tictactoe.web.model.SignUpRequest;
import game.auth.tictactoe.web.model.UserDto;
import jakarta.validation.Valid;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Validated
@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        log.info("/api/auth/signup received POST {}", signUpRequest);

        authService.signup(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User signed up successfully");
    }

    @GetMapping("/login")
    public ResponseEntity<UUID> authorize(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        log.info("/api/auth/login received GET + {}", authorizationHeader);

        return ResponseEntity.ok(authService.authenticate(authorizationHeader));
    }

    @GetMapping("/user/{uuid}")
    public ResponseEntity<UserDto> getUser(@PathVariable UUID uuid) {
        log.info("/api/auth/user/{} received GET", uuid);

        Optional<UserDto> user = userService.findByUuid(uuid);

        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
