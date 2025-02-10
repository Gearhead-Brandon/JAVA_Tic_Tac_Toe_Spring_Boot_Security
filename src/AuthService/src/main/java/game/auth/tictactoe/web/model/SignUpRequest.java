package game.auth.tictactoe.web.model;

import jakarta.validation.constraints.NotNull;

public record SignUpRequest(@NotNull String username, @NotNull String password) {}
