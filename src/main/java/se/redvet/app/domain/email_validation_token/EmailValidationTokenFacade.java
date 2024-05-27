package se.redvet.app.domain.email_validation_token;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.redvet.app.domain.email_validation_token.dto.EmailValidationTokenDto;
import se.redvet.app.infrastructure.text_literal.TextLiteral;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmailValidationTokenFacade {
    private final EmailValidationTokenRepo emailValidationTokenRepo;

    public String createEmailValidationToken(final String username) {
        return Optional.of(username)
                .map(string -> EmailValidationTokenMapper.mapFromUsernameToEmailValidationToken(UUID.randomUUID().toString(), string))
                .map(emailValidationTokenRepo::save)
                .map(EmailValidationTokenMapper::mapFromEmailValidationTokenToString)
                .orElseThrow(() -> new TokenNotCreatedException(TextLiteral.TOKEN_NOT_CREATED));
    }
    public EmailValidationTokenDto findEmailValidationToken(final String token) {
        return emailValidationTokenRepo.findByToken(token)
                .map(EmailValidationTokenMapper::mapFromEmailValidationTokenToEmailValidationTokenDto)
                .orElseThrow(() -> new TokenNotFoundException(TextLiteral.TOKEN_NOT_FOUND));
    }
    @Transactional
    public void removeToken(final String token) {
        emailValidationTokenRepo.removeByToken(token);
    }
}
