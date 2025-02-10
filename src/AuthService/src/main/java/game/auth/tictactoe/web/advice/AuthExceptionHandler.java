package game.auth.tictactoe.web.advice;

import game.auth.tictactoe.exception.InvalidCredentialsException;
import game.auth.tictactoe.exception.MissingAuthorizationException;
import game.auth.tictactoe.exception.UsernameAlreadyExistsException;
import game.auth.tictactoe.web.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExists(UsernameAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        new ErrorResponse(
                            "error",
                            e.getMessage(),
                            String.valueOf(HttpStatus.CONFLICT.value())
                        )
                );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException e) {
        log.info("INVALID >>>> {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        new ErrorResponse(
                            "error",
                            e.getMessage(),
                            String.valueOf(HttpStatus.UNAUTHORIZED.value())
                        )
                );
    }

    @ExceptionHandler(MissingAuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleMissingAuthorization(MissingAuthorizationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header("WWW-Authenticate", "Basic realm=\"Access to protected resource\"")
                .body(
                        new ErrorResponse(
                            "error",
                            e.getMessage(),
                            String.valueOf(HttpStatus.UNAUTHORIZED.value())
                        )
                );
    }
}
