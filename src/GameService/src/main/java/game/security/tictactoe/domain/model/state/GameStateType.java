package game.security.tictactoe.domain.model.state;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GameStateType {
    WAITING_FOR_PLAYER,
    PLAYER_TURN, // with uuid
    COMPUTER_TURN,
    PLAYER_WON, // with uuid
    DRAW

//    private final String value;

//    public static GameStateType getGameStateType(GameState state) {
//        return switch (state) {
//            case WaitingForPlayerState _ -> WAITING_FOR_PLAYER;
//            case PlayerTurnState _ -> PLAYER_TURN;
//            case PlayerWinState _ -> PLAYER_WON;
//            case DrawGameState _ -> DRAW;
//            default -> null;
//        };
//    }
}
