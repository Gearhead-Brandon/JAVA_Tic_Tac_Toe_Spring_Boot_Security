package game.auth.tictactoe.exception;

public class MissingAuthorizationException extends RuntimeException {
    public MissingAuthorizationException(String message) {
        super(message);
    }
}
