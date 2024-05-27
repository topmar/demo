package se.redvet.app.infrastructure.email_validation.error;

import org.springframework.http.HttpStatus;

record ResponseEmailValidationError(
        String message,
        HttpStatus status
) {
}
