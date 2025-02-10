package game.security.tictactoe.domain.model.state;

import game.security.tictactoe.domain.model.game.GameField;
import game.security.tictactoe.exception.InvalidGameJoiningException;
import game.security.tictactoe.exception.InvalidPlayerTurnException;

import java.util.UUID;

public interface GameState {

    default void onMove(GameField newField, UUID userId){
        throw new InvalidPlayerTurnException("Can't make move in this state");
    }

    default void onPlayerJoined(UUID uuid){
        throw new InvalidGameJoiningException("Can't join game in this state");
    }

    String getStatusMessage();

    GameStateType getGameStateType();

    default UUID getCurrentPlayerUuid(){
        return null;
    }
}
