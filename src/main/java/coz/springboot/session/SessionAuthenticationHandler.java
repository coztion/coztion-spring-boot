package coz.springboot.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import coz.springboot.core.user.vo.SessionUser;
import coz.springboot.helper.RedisRepository;
import coz.springboot.helper.SessionFactory;

@Component
@RequiredArgsConstructor
public class SessionAuthenticationHandler {

    public static final String REFRESH_TOKEN_COOKIE_NAME = "REFRESH_TOKEN";

    private final SessionFactory sessionFactory;

    private final RedisRepository redisRepository;

    public void handleAuthenticationSuccess(SessionUser sessionUser, HttpServletResponse response) {
        String accessToken = sessionFactory.generateAccessToken(sessionUser);
        String refreshToken = sessionFactory.generateRefreshToken(sessionUser);

        response.setHeader("Authorization", "Bearer " + accessToken);

        Cookie refreshTokenCookie = new Cookie("REFRESH_TOKEN", refreshToken);
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
        Cookie refreshTokenCookie = new Cookie("REFRESH_TOKEN", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/session/refresh");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);
    }
}
