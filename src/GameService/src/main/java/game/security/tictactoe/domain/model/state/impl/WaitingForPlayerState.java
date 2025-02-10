package game.security.tictactoe.domain.model.state.impl;

import game.security.tictactoe.domain.factory.GameStateFactory;
import game.security.tictactoe.domain.factory.MoveStrategyFactory;
import game.security.tictactoe.domain.model.game.CellType;
import game.security.tictactoe.domain.model.game.Game;
import game.security.tictactoe.domain.model.player.Player;
import game.security.tictactoe.domain.model.player.move.MoveStrategy;
import game.security.tictactoe.domain.model.player.move.MoveStrategyType;
import game.security.tictactoe.domain.model.state.GameState;
import game.security.tictactoe.domain.model.state.GameStateType;
import game.security.tictactoe.exception.InvalidGameJoiningException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class WaitingForPlayerState implements GameState {

    private final Game game;

    @Override
    public void onPlayerJoined(UUID uuid) {
        if(game.getSecondPlayer() != null){
            throw new InvalidGameJoiningException("There is already a second player in the game");
        }

        log.info("Player joined: {}", uuid);

        final Player newPlayer = new Player(
                uuid,
                (game.getFirstPlayer().getSide() == CellType.X) ? CellType.O : CellType.X,
                null
        );

        game.addPlayer(newPlayer);

        if(newPlayer.isComputer()) {
            log.info("Computer switch strategy");
            newPlayer.setMoveStrategy(defineMoveStrategy(game.getFirstPlayer().getSide()));
            log.info("Computer strategy: {}", newPlayer.getMoveStrategy());
            game.setState(GameStateFactory.createGameState(GameStateType.COMPUTER_TURN, game, null));
        }else {
            newPlayer.setMoveStrategy(MoveStrategyFactory.createMoveStrategy(MoveStrategyType.DEFAULT));
            game.setState(GameStateFactory.createGameState(GameStateType.PLAYER_TURN, game,  getNextTurnPlayerUuid(newPlayer)));
        }
    }

    private MoveStrategy defineMoveStrategy(CellType firstPlayerSide) {
        final MoveStrategyType type = (firstPlayerSide == CellType.X) ? MoveStrategyType.MINIMAX : MoveStrategyType.RANDOM;
        return MoveStrategyFactory.createMoveStrategy(type);
    }

    public UUID getNextTurnPlayerUuid(Player newPlayer) {
        return newPlayer.isComputer() ? Player.COMPUTER_UUID : game.getSecondPlayer().getId();
    }

    @Override
    public String getStatusMessage() {
        return "Waiting for second player";
    }

    @Override
    public GameStateType getGameStateType() {
        return GameStateType.WAITING_FOR_PLAYER;
    }
}
