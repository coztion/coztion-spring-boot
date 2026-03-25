package coz.springboot.common.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import coz.springboot.common.helper.RedisRepository;
import coz.springboot.common.helper.SessionFactory;
import coz.springboot.user.model.SessionUser;

@Component
@RequiredArgsConstructor
public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    @Value("${spring.security.cookie.refresh-token}")
    private String refreshTokenCookieName;

    private final SessionFactory sessionFactory;

    private final RedisRepository redisRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SessionUser sessionUser = (SessionUser) authentication.getPrincipal();
        handleAuthenticationSuccess(sessionUser, response);
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        handleAuthenticationFailure(response);
    }

    public void handleAuthenticationSuccess(SessionUser sessionUser, HttpServletResponse response) {
        String accessToken = sessionFactory.generateAccessToken(sessionUser);
        String refreshToken = sessionFactory.generateRefreshToken(sessionUser);

        response.setHeader("Authorization", "Bearer " + accessToken);

        Cookie refreshTokenCookie = new Cookie(refreshTokenCookieName, refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/session/refresh");
        refreshTokenCookie.setMaxAge(sessionFactory.getRefreshTokenExpirationSeconds());
        response.addCookie(refreshTokenCookie);

        redisRepository.save(
                sessionUser.getUsername(), refreshToken, sessionFactory.getRefreshTokenExpirationSeconds());
    }

    public void handleAuthenticationFailure(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        removeRefreshTokenCookie(response);
    }

    public void removeRefreshTokenCookie(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie(refreshTokenCookieName, null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/session/refresh");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);
    }
}
