package se.redvet.app.infrastructure.email_validation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.redvet.app.domain.email_validation_token.EmailValidationTokenFacade;
import se.redvet.app.domain.user.UserFacade;

import java.util.Optional;

@RestController
@RequestMapping("/api/valid")
@RequiredArgsConstructor
public class EmailValidationController {
    private final EmailValidationTokenFacade emailValidationTokenFacade;
    private final UserFacade userFacade;

    @GetMapping("{uuid}")
    public ResponseEntity<String> emailValid(@PathVariable("uuid") String uuid) {
        return Optional.of(emailValidationTokenFacade.findEmailValidationToken(uuid))
                .map(emailValidationTokenDto -> {
                    userFacade.enableUser(emailValidationTokenDto.username());
                    emailValidationTokenFacade.removeToken(emailValidationTokenDto.token());
                    return ResponseEntity.ok("success");
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
