package se.redvet.app.domain.email_validation_token;


import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.redvet.app.domain.email_validation_token.dto.EmailValidationTokenDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class EmailValidationTokenFacadeTest {
    EmailValidationTokenFacade emailValidationTokenFacade = new EmailValidationTokenFacade(new InMemoryEmailValidTokenRepo());
    private static final String USERNAME = "username";
    private static final int UUID_CHARACTER_LENGTH = 36;

    @Test
    void should_create_email_validation_token_successfully() {
        //given
        //when
        String token = emailValidationTokenFacade.createEmailValidationToken(USERNAME);
        //then
        Assertions.assertNotNull(token);
        assertThat(token).hasSize(UUID_CHARACTER_LENGTH);
    }

    @Test
    void should_find_token_successfully() {
        //given
        String token = emailValidationTokenFacade.createEmailValidationToken(USERNAME);
        //when
        EmailValidationTokenDto result = emailValidationTokenFacade.findEmailValidationToken(token);
        //then
        Assertions.assertEquals(USERNAME, result.username());
    }

    @Test
    void should_throw_exception_when_token_not_found() {
        //given
        String token = emailValidationTokenFacade.createEmailValidationToken(USERNAME);
        //when
        Throwable thrown = catchThrowable(() -> emailValidationTokenFacade.findEmailValidationToken(USERNAME));
        //then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(TokenNotFoundException.class)
                .hasMessage("Token not found");
    }

    @Test
    void should_remove_token_successfully() {
        //given
        String token = emailValidationTokenFacade.createEmailValidationToken(USERNAME);
        emailValidationTokenFacade.removeToken(token);
        //when
        Throwable thrown = catchThrowable(() -> emailValidationTokenFacade.findEmailValidationToken(token));
        //then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(TokenNotFoundException.class)
                .hasMessage("Token not found");
    }

}