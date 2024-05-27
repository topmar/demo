package se.redvet.app.infrastructure.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("auth.jwt")
public record JwtConfigurationProperties(
        String secret,
        long expirationMs,
        String issuer
)  {
}
