package se.redvet.app.infrastructure.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import se.redvet.app.domain.user.UserFacade;
import se.redvet.app.domain.user.dto.UserLoginDto;

import java.util.Collections;

@AllArgsConstructor
class LoginUserDetailsService implements UserDetailsService {
    private final UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserLoginDto userLoginDto = userFacade.findUserForLogin(username);
        return getUser(userLoginDto);
    }

    private org.springframework.security.core.userdetails.User getUser(
            final UserLoginDto user) {
        return new org.springframework.security.core.userdetails.User(
                user.username(),
                user.password(),
                user.enabled(),
                user.accountNonExpired(),
                user.credentialsNonExpired(),
                user.accountNonLocked(),
                Collections.singleton( new SimpleGrantedAuthority("USER"))
        );
    }
}
