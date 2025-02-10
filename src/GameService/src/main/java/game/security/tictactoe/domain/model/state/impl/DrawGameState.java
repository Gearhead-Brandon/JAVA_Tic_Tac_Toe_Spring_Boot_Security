package game.security.tictactoe.domain.model.state.impl;

import game.security.tictactoe.domain.model.state.GameState;
import game.security.tictactoe.domain.model.state.GameStateType;

public class DrawGameState implements GameState {
    @Override
    public String getStatusMessage() {
        return "Draw";
    }

    @Override
    public GameStateType getGameStateType() {
        return GameStateType.DRAW;
    }
}
