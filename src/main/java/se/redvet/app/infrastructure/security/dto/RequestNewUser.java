package se.redvet.app.infrastructure.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RequestNewUser(
        @NotBlank(message = "{username.not.blank}")
        @NotEmpty(message = "{username.not.empty}")
        @Pattern(regexp = "^[^@]*$", message = "{username.invalid}")
        @Size(max = 50, message = "{username.too.long}")
        String username,
        @Size(max = 100, message = "{firstname.too.long}")
        String firstName,
        @Size(max = 100, message = "{lastname.too.long}")
        String lastName,
        @NotBlank(message = "{password.not.blank}")
        @NotEmpty(message = "{password.not.empty}")
        @Size(max = 255, message = "{password.too.long}")
        String password,
        @NotBlank
        @NotEmpty
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$", message = "{email.invalid}")
        @Size(max = 100, message = "{email.too.long}")
        String email
) {
}
