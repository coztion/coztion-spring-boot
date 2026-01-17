package coz.springboot.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import coz.springboot.core.user.vo.SessionUser;
import coz.springboot.helper.RedisRepository;
import coz.springboot.helper.SessionFactory;

@Component
@RequiredArgsConstructor
public class SessionAuthenticationHandler {

    @Value("${spring.security.refresh-token-cookie-name}")
    private String refreshTokenCookieName;

    private final SessionFactory sessionFactory;

    private final RedisRepository redisRepository;

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
