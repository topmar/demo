package se.redvet.app.infrastructure.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record RequestSignIn(
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$|^[^@]+$", message = "{username.or.email.invalid}")
        String username,
        @NotBlank(message = "{password.not.blank}")
        @NotEmpty(message = "{password.not.empty}")
        String password
) {
}
