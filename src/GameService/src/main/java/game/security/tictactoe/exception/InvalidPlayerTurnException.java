package game.security.tictactoe.exception;

public class InvalidPlayerTurnException extends RuntimeException {
    public InvalidPlayerTurnException(String message) {
        super(message);
    }
}
