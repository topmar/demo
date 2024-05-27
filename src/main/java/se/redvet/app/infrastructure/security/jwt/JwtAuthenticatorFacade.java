package se.redvet.app.infrastructure.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import se.redvet.app.infrastructure.security.dto.RequestSignIn;
import se.redvet.app.infrastructure.security.dto.ResponseSignIn;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class JwtAuthenticatorFacade {
    private final JwtConfigurationProperties properties;
    private final AuthenticationManager authenticationManager;
    private final Clock clock;
    public ResponseSignIn authenticateAndGenerateToken(final RequestSignIn requestSignIn) {
        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(requestSignIn.username(), requestSignIn.password()));
        User user = (User) authenticate.getPrincipal();

        return ResponseSignIn.builder()
                .token(createToken(user))
                .build();
    }
    private String createToken(final User user) {
        Algorithm algorithm = Algorithm.HMAC512(properties.secret());
        Instant now = LocalDateTime.now(clock).toInstant(ZoneOffset.UTC);
        Instant expiresAT = now.plusMillis(properties.expirationMs());
        String issuer = properties.issuer();
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiresAT)
                .withIssuer(issuer)
                .withClaim("roles", user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .sign(algorithm);
    }
}
