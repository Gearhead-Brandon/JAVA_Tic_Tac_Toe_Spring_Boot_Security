//package game.security.tictactoe.domain.utils;
//
//import game.security.tictactoe.domain.model.state.GameState;
//import game.security.tictactoe.domain.model.state.GameStateType;
//
//public class GameStatusMessageGenerator {
//
//    private GameStatusMessageGenerator() {}
//
//    public static String generateMessage(GameState gameState) {
//        final GameStateType gameStateType = GameStateType.getGameStateType(gameState);
//
//        switch (gameStateType) {
//            case WAITING_FOR_PLAYER:
//                return "Waiting for player";
//            case PLAYER_TURN:
//                return "Your turn";
//            case PLAYER_WON:
//                return "You won";
//            case DRAW:
//                return "Draw";
//        }
//
//        return null;
//    }
//}
