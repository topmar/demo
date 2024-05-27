package se.redvet.app.domain.email_validation_token.dto;

import lombok.Builder;

@Builder
public record EmailValidationTokenDto(
        String token,
        String username
) {
}
