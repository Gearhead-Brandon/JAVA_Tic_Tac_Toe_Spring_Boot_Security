package game.security.tictactoe.domain.service.auth;

import game.security.tictactoe.web.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "auth-service", path = "api/auth")
public interface AuthClient {

    @GetMapping("/login")
    ResponseEntity<UUID> login();

    @GetMapping("/user/{uuid}")
    UserDTO getUser(@PathVariable UUID uuid);
}
