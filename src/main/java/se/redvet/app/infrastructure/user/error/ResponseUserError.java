package se.redvet.app.infrastructure.user.error;

import org.springframework.http.HttpStatus;

record ResponseUserError(
        String message,
        HttpStatus status
) {
}
