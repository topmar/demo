package se.redvet.app.domain.user;

public class UserNotCreatedException extends RuntimeException {
    UserNotCreatedException(final String userNotCreated) {
        super(userNotCreated);
    }
}