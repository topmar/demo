package se.redvet.app.domain.user;

public class UserNotFoundException extends RuntimeException {
    UserNotFoundException(final String userNotFound) {
        super(userNotFound);
    }
}