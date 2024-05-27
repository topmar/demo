package se.redvet.app.infrastructure.email_validation.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import se.redvet.app.domain.email_validation_token.TokenNotFoundException;

@ControllerAdvice
@Log4j2
public class EmailValidationControllerErrorHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TokenNotFoundException.class)
    @ResponseBody
    public ResponseEmailValidationError tokenNotFound(TokenNotFoundException e) {
        final String message = "Bad token";
        log.warn(message);
        return new ResponseEmailValidationError(message, HttpStatus.BAD_REQUEST);
    }
}
