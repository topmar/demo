package se.redvet.app.infrastructure.apivalidation;

import org.springframework.http.HttpStatus;

import java.util.List;

record ApiValidationErrorDto(
        List<String> messages,
        HttpStatus status
) {
}
