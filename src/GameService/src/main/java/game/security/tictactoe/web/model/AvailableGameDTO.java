package game.security.tictactoe.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class AvailableGameDTO{

    @JsonProperty("uuid")
    private final UUID uuid;

    @JsonProperty("side")
    private final String  side;
}
