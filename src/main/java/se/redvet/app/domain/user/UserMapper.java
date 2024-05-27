package se.redvet.app.domain.user;

import se.redvet.app.domain.user.dto.UserDto;
import se.redvet.app.domain.user.dto.UserLoginDto;
import se.redvet.app.infrastructure.security.dto.RequestNewUser;
import se.redvet.app.infrastructure.user.dto.RequestUser;

class UserMapper {
    private UserMapper() {throw new IllegalStateException("UserMapper class");}

    public static UserLoginDto mapFromUserToUserLoginDto(final User user) {
        return UserLoginDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .accountNonExpired(user.isAccountNonExpired())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .build();
    }

    public static UserDto mapFromUserToUserDto(final User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static User mapFromRequestNewUserToUser(final RequestNewUser requestNewUser) {
        return User.builder()
                .username(requestNewUser.username())
                .firstName(requestNewUser.firstName())
                .lastName(requestNewUser.lastName())
                .email(requestNewUser.email())
                .password(requestNewUser.password())
                .build();
    }

    public static User mapFromRequestUserToExistUser(final User user, final RequestUser requestUser) {
        user.setUsername(requestUser.username());
        user.setFirstName(requestUser.firstName());
        user.setLastName(requestUser.lastName());
        user.setEmail(requestUser.email());
        return user;
    }
}
