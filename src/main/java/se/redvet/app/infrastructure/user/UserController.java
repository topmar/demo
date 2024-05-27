package se.redvet.app.infrastructure.user;

import com.auth0.jwt.JWT;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.redvet.app.domain.user.UserFacade;
import se.redvet.app.domain.user.dto.UserDto;
import se.redvet.app.infrastructure.text_literal.TextLiteral;
import se.redvet.app.infrastructure.user.dto.RequestUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserFacade userFacade;

    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser(@RequestHeader("Authorization") String jwtToken) {
        return ResponseEntity.ok().body(userFacade.getUser(getUsernameFromJwtToken(jwtToken)));
    }
    @PutMapping("/user")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid RequestUser requestUser,
                                              @RequestHeader("Authorization") String jwtToken) {
        return ResponseEntity.ok().body(userFacade.updateUser(getUsernameFromJwtToken(jwtToken), requestUser));
    }
    @DeleteMapping("/user")
    public ResponseEntity<String> removeUser(@RequestHeader("Authorization") String jwtToken) {
        userFacade.removeUser(getUsernameFromJwtToken(jwtToken));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(TextLiteral.USER_WAS_REMOVED);
    }
    private String getUsernameFromJwtToken(String jwtToken) {
        return JWT.decode(jwtToken.replace("Bearer ", "")).getSubject();
    }
}
