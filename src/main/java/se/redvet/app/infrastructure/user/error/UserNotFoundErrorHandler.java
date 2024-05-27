package se.redvet.app.infrastructure.user.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import se.redvet.app.domain.user.UserNotFoundException;

@ControllerAdvice
@Log4j2
public class UserNotFoundErrorHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseUserError userNotFound(DataIntegrityViolationException e) {
        final String message = e.getMessage();
        log.warn(message);
        return new ResponseUserError(message, HttpStatus.NOT_FOUND);
    }
}
