package game.security.tictactoe.domain.model.state.impl;

import game.security.tictactoe.domain.factory.GameStateFactory;
import game.security.tictactoe.domain.model.game.CellType;
import game.security.tictactoe.domain.model.game.Game;
import game.security.tictactoe.domain.model.game.GameField;
import game.security.tictactoe.domain.model.state.GameState;
import game.security.tictactoe.domain.model.state.GameStateType;
import game.security.tictactoe.domain.service.WinState;
import game.security.tictactoe.domain.utils.GameUtil;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public abstract class TurnState implements GameState {

    protected final Game game;

    @Override
    public void onMove(GameField newGameField, UUID userId) {
        getLogMessage();

        checkUserId(userId);

        validateChanges(newGameField);

        makeMove();

        if(!isGameFinished()) {
            passTurnToPlayer();
        }
    }

    protected abstract void getLogMessage();

    protected abstract void checkUserId(UUID userId);

    protected abstract void validateChanges(GameField gameField);

    protected abstract void makeMove();

    protected abstract void passTurnToPlayer();

    private boolean isGameFinished() {

        final WinState winState = GameUtil.isGameOver(game.getGameField());

//        if(winState.equals(WinState.DRAW))
//            game.setState(GameStateFactory.createGameState(GameStateType.DRAW, game, null));
//
//        if(winState.equals(WinState.X_WON))
//            game.setState(GameStateFactory.createGameState(GameStateType.PLAYER_WON, game, getUUIDPlayerBySide(CellType.X)));
//
//        if(winState.equals(WinState.O_WON))
//            game.setState(GameStateFactory.createGameState(GameStateType.PLAYER_WON, game, getUUIDPlayerBySide(CellType.O)));

        switch (winState) {
            case X_WON -> game.setState(GameStateFactory.createGameState(GameStateType.PLAYER_WON, game, getUUIDPlayerBySide(CellType.X)));
            case O_WON -> game.setState(GameStateFactory.createGameState(GameStateType.PLAYER_WON, game, getUUIDPlayerBySide(CellType.O)));
            case DRAW -> game.setState(GameStateFactory.createGameState(GameStateType.DRAW, game, null));
            default -> { return false; }
        }

        return true;
    }

    protected UUID getUUIDPlayerBySide(CellType side) {
        return game.getFirstPlayer().getSide().equals(side) ? game.getFirstPlayer().getId() : game.getSecondPlayer().getId();
    }
}
