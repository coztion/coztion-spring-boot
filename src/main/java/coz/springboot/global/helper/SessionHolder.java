package coz.springboot.global.helper;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import lombok.experimental.UtilityClass;

import coz.springboot.user.domain.Authority;
import coz.springboot.user.domain.SessionUser;

@UtilityClass
public class SessionHolder {

    public static SessionUser getSessionUser() {
        return (SessionUser)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getUsername() {
        return getSessionUser().getUsername();
    }

    public static List<Authority> getAuthorities() {
        return getSessionUser().getAuthorityCodes().stream()
                .map(Authority::ofValue)
                .toList();
    }
}
