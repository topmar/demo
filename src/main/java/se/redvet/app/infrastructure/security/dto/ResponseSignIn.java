package se.redvet.app.infrastructure.security.dto;

import lombok.Builder;

@Builder
public record ResponseSignIn(
        String token
) {
}
