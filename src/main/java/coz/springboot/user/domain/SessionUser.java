package coz.springboot.user.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SessionUser implements UserDetails {

    private String username;

    private String password;

    private List<String> authorityCodes;

    public static SessionUser ofUser(User user) {
        return SessionUser.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorityCodes(user.getAuthorities().stream()
                        .map(Authority::getAuthorityCode)
                        .toList())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityCodes.stream()
                .map(role -> (GrantedAuthority) () -> role)
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
