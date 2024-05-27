package se.redvet.app.infrastructure.security;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.redvet.app.domain.email_validation_token.EmailValidationTokenFacade;
import se.redvet.app.domain.user.UserFacade;
import se.redvet.app.domain.user.dto.UserDto;
import se.redvet.app.infrastructure.email_sender.EmailService;
import se.redvet.app.infrastructure.security.dto.RequestNewUser;
import se.redvet.app.infrastructure.security.dto.RequestSignIn;
import se.redvet.app.infrastructure.security.dto.ResponseSignIn;
import se.redvet.app.infrastructure.security.jwt.JwtAuthenticatorFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
class SecurityController {
    private final JwtAuthenticatorFacade jwtAuthenticatorFacade;
    private final UserFacade userFacade;
    private final EmailValidationTokenFacade emailValidationTokenFacade;
    private final EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<ResponseSignIn> authenticateAndGenerateToken(
            @Valid @RequestBody final RequestSignIn requestSignIn) {
        return ResponseEntity.ok(jwtAuthenticatorFacade.authenticateAndGenerateToken(requestSignIn));
    }
    @PostMapping("/signup")
    public ResponseEntity<UserDto> registerNewUser(@Valid @RequestBody final RequestNewUser requestNewUser) {
        UserDto userDto = userFacade.addUser(requestNewUser);
        emailService.sendEmail(userDto.email(), emailValidationTokenFacade.createEmailValidationToken(userDto.username()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
}
