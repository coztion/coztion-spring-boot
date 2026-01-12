package coz.springboot.helper;

import org.springframework.security.core.context.SecurityContextHolder;

import lombok.experimental.UtilityClass;

import coz.springboot.core.user.vo.SessionUser;

@UtilityClass
public class SessionHolder {

    public static SessionUser getSessionUser() {
        return (SessionUser)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getUsername() {
        return getSessionUser().getUsername();
    }
}
