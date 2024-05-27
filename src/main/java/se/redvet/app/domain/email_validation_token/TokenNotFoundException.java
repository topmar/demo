package se.redvet.app.domain.email_validation_token;

public class TokenNotFoundException extends RuntimeException {
    TokenNotFoundException(final String tokenNotFound) {
        super(tokenNotFound);
    }
}