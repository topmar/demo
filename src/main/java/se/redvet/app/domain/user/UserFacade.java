package se.redvet.app.domain.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.redvet.app.domain.user.dto.UserDto;
import se.redvet.app.domain.user.dto.UserLoginDto;
import se.redvet.app.infrastructure.security.dto.RequestNewUser;
import se.redvet.app.infrastructure.text_literal.TextLiteral;
import se.redvet.app.infrastructure.user.dto.RequestUser;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserFacade {
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserRepo userRepo;

    public UserLoginDto findUserForLogin(final String usernameOrEmail) {
        return userRepo.findByUsernameOrEmail(usernameOrEmail)
                .map(UserMapper::mapFromUserToUserLoginDto)
                .orElseThrow(() -> new UserNotFoundException(TextLiteral.USER_NOT_FOUND));
    }
    public UserDto addUser(final RequestNewUser requestNewUser) {
        return Optional.of(requestNewUser)
                .map(UserMapper::mapFromRequestNewUserToUser)
                .map(user -> {
                    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                    return user;
                })
                .map(userRepo::save)
                .map(UserMapper::mapFromUserToUserDto)
                .orElseThrow(() -> new UserNotCreatedException(TextLiteral.USER_NOT_CREATED));
    }

    public UserDto getUser(final String userName) {
        return userRepo.findByUsername(userName)
                .map(UserMapper::mapFromUserToUserDto)
                .orElseThrow(() -> new UserNotFoundException(TextLiteral.USER_NOT_FOUND));
    }

    public UserDto updateUser(final String username, final RequestUser requestUser) {
        return userRepo.findByUsername(username)
                .map(user -> UserMapper.mapFromRequestUserToExistUser(user, requestUser))
                .map(userRepo::save)
                .map(UserMapper::mapFromUserToUserDto)
                .orElseThrow(() -> new UserNotUpdatedException(TextLiteral.USER_NOT_UPDATED));
    }
    public void enableUser(final String username) {
        userRepo.findByUsername(username)
                .ifPresent(user -> {
                    user.setEnabled(true);
                    userRepo.save(user);
                });
    }

    @Transactional
    public void removeUser(final String username) {
        userRepo.removeByUsername(username);
    }
}
