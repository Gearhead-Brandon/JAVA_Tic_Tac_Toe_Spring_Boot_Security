package game.security.tictactoe.domain.factory;

import game.security.tictactoe.domain.model.game.Game;
import game.security.tictactoe.domain.model.state.GameState;
import game.security.tictactoe.domain.model.state.GameStateType;
import game.security.tictactoe.domain.model.state.impl.*;

import java.util.UUID;

public class GameStateFactory {

    private GameStateFactory() {}

    public static GameState createGameState(GameStateType type, Game game, UUID playerUuid) {
        return switch (type) {
            case GameStateType.WAITING_FOR_PLAYER -> new WaitingForPlayerState(game);
            case GameStateType.PLAYER_TURN -> new PlayerTurnState(game,playerUuid);
            case GameStateType.COMPUTER_TURN -> new ComputerTurnState(game);
            case GameStateType.PLAYER_WON -> new PlayerWinState(playerUuid);
            case GameStateType.DRAW -> new DrawGameState();
        };
    }
}
