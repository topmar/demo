package se.redvet.app.domain.user.dto;

import lombok.Builder;

@Builder
public record UserLoginDto(
        int id,
        String username,
        String password,
        boolean enabled,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired
) {
}
