package se.redvet.app.domain.email_validation_token;

import se.redvet.app.domain.email_validation_token.dto.EmailValidationTokenDto;

class EmailValidationTokenMapper {
    private EmailValidationTokenMapper() {throw new IllegalStateException("EmailTokenMapper class");}

    public static EmailValidationToken mapFromUsernameToEmailValidationToken(final String uuid, final String username) {
        return EmailValidationToken.builder()
                .token(uuid)
                .username(username)
                .build();
    }

    public static EmailValidationTokenDto mapFromEmailValidationTokenToEmailValidationTokenDto(EmailValidationToken emailToken) {
        return EmailValidationTokenDto.builder()
                .token(emailToken.getToken())
                .username(emailToken.getUsername())
                .build();
    }

    public static String mapFromEmailValidationTokenToString(EmailValidationToken emailToken) {
        return emailToken.getToken();
    }
}
