package se.redvet.app.infrastructure.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record RequestUser(
        @NotBlank(message = "{password.not.blank}")
        @NotEmpty(message = "{password.not.empty}")
        @Pattern(regexp = "^[^@]*$", message = "{username.invalid}")
        String username,
        String firstName,
        String lastName,
        @Email
        String email
) {
}
