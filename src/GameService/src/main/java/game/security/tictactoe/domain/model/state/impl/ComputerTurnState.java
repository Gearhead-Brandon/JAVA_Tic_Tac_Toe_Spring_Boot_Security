package game.security.tictactoe.domain.model.state.impl;

import game.security.tictactoe.domain.factory.GameStateFactory;
import game.security.tictactoe.domain.factory.MoveStrategyFactory;
import game.security.tictactoe.domain.model.game.Game;
import game.security.tictactoe.domain.model.game.GameField;
import game.security.tictactoe.domain.model.player.Player;
import game.security.tictactoe.domain.model.player.move.MoveStrategyType;
import game.security.tictactoe.domain.model.state.GameState;
import game.security.tictactoe.domain.model.state.GameStateType;
import game.security.tictactoe.exception.InvalidPlayerTurnException;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class ComputerTurnState extends TurnState implements GameState {

    public ComputerTurnState(Game game) {
        super(game);
    }

    @Override
    public String getStatusMessage() {
        return "Computer turn";
    }

    @Override
    public GameStateType getGameStateType() {
        return GameStateType.COMPUTER_TURN;
    }

    @Override
    protected void getLogMessage() {
        log.info("Computer {} turn", game.getSecondPlayer());
    }

    @Override
    protected void checkUserId(UUID userId){
        if(!Player.COMPUTER_UUID.equals(userId))
            throw new InvalidPlayerTurnException("Invalid player turn");
    }

    @Override
    protected void validateChanges(GameField gameField) { /*do nothing for computer*/ }

    @Override
    protected void makeMove() {
        if(game.getGameField().isEmpty())
            game.getSecondPlayer().setMoveStrategy(MoveStrategyFactory.createMoveStrategy(MoveStrategyType.RANDOM));
        else
            game.getSecondPlayer().setMoveStrategy(MoveStrategyFactory.createMoveStrategy(MoveStrategyType.MINIMAX));

        game.getSecondPlayer().makeMove(game);
    }

    @Override
    protected void passTurnToPlayer() {
        game.setState(GameStateFactory.createGameState(
                GameStateType.PLAYER_TURN,
                game,
                game.getFirstPlayer().getId())
        );
    }
}
