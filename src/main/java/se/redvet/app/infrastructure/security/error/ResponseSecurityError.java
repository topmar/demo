package se.redvet.app.infrastructure.security.error;

import org.springframework.http.HttpStatus;

record ResponseSecurityError(
        String message,
        HttpStatus status
) {
}
