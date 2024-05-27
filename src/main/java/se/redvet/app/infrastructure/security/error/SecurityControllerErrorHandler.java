package se.redvet.app.infrastructure.security.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class SecurityControllerErrorHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseSecurityError conflict(DataIntegrityViolationException e) {
        final String message;
        if (e.getMessage().contains("username_key")) {
            message = "username is already exist";
        } else if (e.getMessage().contains("users_email_key")) {
            message = "email is already exist";
        } else {
            message = "unknown error";
        }
        log.warn(message);
        return new ResponseSecurityError(message, HttpStatus.CONFLICT);
    }

}
