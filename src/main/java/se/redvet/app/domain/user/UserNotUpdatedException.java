package se.redvet.app.domain.user;

public class UserNotUpdatedException extends RuntimeException {
    UserNotUpdatedException(final String userNotUpdated) {
        super(userNotUpdated);
    }
}