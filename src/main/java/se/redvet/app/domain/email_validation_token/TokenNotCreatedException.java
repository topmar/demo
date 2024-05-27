package se.redvet.app.domain.email_validation_token;

public class TokenNotCreatedException extends RuntimeException {
    TokenNotCreatedException(final String tokenNotCreated) {
        super(tokenNotCreated);
    }
}