package game.auth.tictactoe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Getter
@ToString
@RedisHash("users")
@AllArgsConstructor
public class User {
    @Id
    private UUID id;

    @Indexed
    private String username;
    private String password;
    private String role;
}

