package se.redvet.app.domain.user;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import se.redvet.app.domain.user.dto.UserDto;
import se.redvet.app.domain.user.dto.UserLoginDto;
import se.redvet.app.infrastructure.security.dto.RequestNewUser;
import se.redvet.app.infrastructure.security.dto.RequestSignIn;
import se.redvet.app.infrastructure.user.dto.RequestUser;

import static org.assertj.core.api.Assertions.catchThrowable;

class UserFacadeTest {

    UserFacade userFacade = new UserFacade(
            new BCryptPasswordEncoder(),
            new InMemoryUserRepo()
    );
    private static final int FIRST_USER_ID = 1;
    private static final String USERNAME = "username";
    private static final String USERNAME_2 = "username2";
    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final String EMAIL = "email@email.com";
    private static final String EMAIL_2 = "email2@email.com";
    private static final String PASSWORD = "pass";
    private static final String UPDATED_USERNAME = "new_username";
    private static final String NON_EXISTENT_USERNAME = "someUser";
    private static final int LENGTH_OF_ENCRYPTED_PASSWORD = 60;

    RequestNewUser requestNewUser = new RequestNewUser(USERNAME, FIRST_NAME, LAST_NAME, PASSWORD, EMAIL);
    RequestNewUser requestNewUser2 = new RequestNewUser(USERNAME_2, FIRST_NAME, LAST_NAME, PASSWORD, EMAIL_2);
    RequestUser requestUpdateUser = new RequestUser(UPDATED_USERNAME, FIRST_NAME, LAST_NAME, EMAIL);
    RequestUser requestUpdateUserWithExistUsername = new RequestUser(USERNAME_2, FIRST_NAME, LAST_NAME, EMAIL);
    RequestUser requestUpdateUserWithExistEmail = new RequestUser(USERNAME, FIRST_NAME, LAST_NAME, EMAIL_2);

    @Test
    void should_add_user_successfully() {
        //given
        //when
        UserDto result = userFacade.addUser(requestNewUser);
        UserLoginDto checkIsUserEnabled = userFacade.findUserForLogin(USERNAME);
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(USERNAME, result.username());
        Assertions.assertFalse(checkIsUserEnabled.enabled());
    }
    @Test
    public void should_return_user_for_login_dto_when_username_exists_in_database() {
        //given
        userFacade.addUser(requestNewUser);
        //when
        UserLoginDto userByUsername = userFacade.findUserForLogin(USERNAME);
        //then
        Assertions.assertNotNull(userByUsername);
        Assertions.assertEquals(FIRST_USER_ID, userByUsername.id());
        Assertions.assertEquals(USERNAME, userByUsername.username());
        Assertions.assertEquals(LENGTH_OF_ENCRYPTED_PASSWORD, userByUsername.password().length());
    }
    @Test
    public void should_return_user_for_login_dto_when_email_exists_in_database() {
        //given
        UserDto addedUser = userFacade.addUser(requestNewUser);
        //when
        UserLoginDto userByEmail = userFacade.findUserForLogin(EMAIL);
        //then
        Assertions.assertNotNull(userByEmail);
        Assertions.assertEquals(FIRST_USER_ID, userByEmail.id());
        Assertions.assertEquals(USERNAME, userByEmail.username());
        Assertions.assertEquals(LENGTH_OF_ENCRYPTED_PASSWORD, userByEmail.password().length());
    }
    @Test
    public void should_throw_user_not_found_exception_when_user_not_found_in_database() {
        //given
        //when
        Throwable thrown = catchThrowable(() -> userFacade.findUserForLogin(NON_EXISTENT_USERNAME));
        //then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found");
    }
    @Test
    void should_throw_user_not_created_exception_when_username_exist_in_database() {
        //given
        RequestNewUser newUser = new RequestNewUser(USERNAME, FIRST_NAME, LAST_NAME, PASSWORD, EMAIL);
        userFacade.addUser(newUser);
        //when
        Throwable thrown = catchThrowable(() -> userFacade.addUser(newUser));
        //then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(UserNotCreatedException.class)
                .hasMessage("User not created - duplicate username");
    }
    @Test
    void should_throw_user_not_created_exception_when_email_exist_in_database() {
        //given
        RequestNewUser newUser = new RequestNewUser(USERNAME, FIRST_NAME, LAST_NAME, PASSWORD, EMAIL);
        userFacade.addUser(newUser);
        RequestNewUser newUser2 = new RequestNewUser(USERNAME_2, FIRST_NAME, LAST_NAME, PASSWORD, EMAIL);
        //when
        Throwable thrown = catchThrowable(() -> userFacade.addUser(newUser2));
        //then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(UserNotCreatedException.class)
                .hasMessage("User not created - duplicate email");
    }
    @Test
    void should_return_user_dto_when_username_exists() {
        //given
        userFacade.addUser(requestNewUser);
        //when
        UserDto result = userFacade.getUser(USERNAME);
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(USERNAME, result.username());
    }
    @Test
    void should_throw_user_not_found_exception_when_username_does_not_exist() {
        //given
        //when
        Throwable thrown = catchThrowable(() -> userFacade.getUser(NON_EXISTENT_USERNAME));
        //then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found");
    }
    @Test
    void should_return_user_dto_when_user_is_updated_successfully() {
        //given
        userFacade.addUser(requestNewUser);
        //when
        UserDto result = userFacade.updateUser(USERNAME, requestUpdateUser);
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(UPDATED_USERNAME, result.username());
    }
    @Test
    void should_throw_user_not_updated_exception_when_username_does_not_exist() {
        //given
        //when
        Throwable thrown = catchThrowable(() -> userFacade.updateUser(NON_EXISTENT_USERNAME, requestUpdateUser));
        //then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(UserNotUpdatedException.class)
                .hasMessage("User not updated.");
    }
    @Test
    void should_throw_user_not_updated_exception_when_username_already_exist_in_database() {
        //given
        userFacade.addUser(requestNewUser);
        userFacade.addUser(requestNewUser2);
        //when
        Throwable thrown = catchThrowable(() -> userFacade.updateUser(USERNAME ,requestUpdateUserWithExistUsername));
        //then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(UserNotUpdatedException.class)
                .hasMessage("User not updated - duplicate username.");
    }
    @Test
    void should_throw_user_not_updated_exception_when_email_already_exist_in_database() {
        //given
        userFacade.addUser(requestNewUser);
        userFacade.addUser(requestNewUser2);
        //when
        Throwable thrown = catchThrowable(() -> userFacade.updateUser(USERNAME ,requestUpdateUserWithExistEmail));
        //then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(UserNotUpdatedException.class)
                .hasMessage("User not updated - duplicate email.");
    }
    @Test
    void should_enable_user_successfully() {
        //given
        userFacade.addUser(requestNewUser);
        //when
        userFacade.enableUser(USERNAME);
        UserLoginDto result = userFacade.findUserForLogin(USERNAME);
        //then
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.enabled());
    }
    @Test
    void should_remove_user_successfully() {
        //given
        userFacade.addUser(requestNewUser);
        //when
        userFacade.removeUser(USERNAME);
        Throwable thrown = catchThrowable(() -> userFacade.getUser(NON_EXISTENT_USERNAME));
        //then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found");
    }

}