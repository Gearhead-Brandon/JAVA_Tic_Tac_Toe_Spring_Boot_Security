package game.security.tictactoe.domain.model.state.impl;

import game.security.tictactoe.domain.model.state.GameState;
import game.security.tictactoe.domain.model.state.GameStateType;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class PlayerWinState implements GameState {

    private final UUID playerUuid;

    @Override
    public String getStatusMessage() {
        return "Player " + playerUuid + " won";
    }

    @Override
    public GameStateType getGameStateType() {
        return GameStateType.PLAYER_WON;
    }

    @Override
    public UUID getCurrentPlayerUuid() {
        return playerUuid;
    }
}
