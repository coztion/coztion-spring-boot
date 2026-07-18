package coz.springboot.common.security;

import java.time.Duration;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import tools.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import coz.springboot.common.dto.RestResponse;
import coz.springboot.common.helper.RedisRepository;
import coz.springboot.common.helper.SessionFactory;
import coz.springboot.user.domain.SessionUser;

@Component
@RequiredArgsConstructor
public class AuthenticationHandler
        implements AuthenticationSuccessHandler, AuthenticationFailureHandler, AuthenticationEntryPoint {

    @Value("${spring.security.cookie.access-token}")
    private String accessTokenCookieName;

    @Value("${spring.security.cookie.refresh-token}")
    private String refreshTokenCookieName;

    @Value("${spring.security.url.refresh}")
    private String refreshUrl;

    @Value("${spring.security.cookie.secure}")
    private boolean secureCookie;

    private final SessionFactory sessionFactory;

    private final RedisRepository redisRepository;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SessionUser sessionUser = (SessionUser) authentication.getPrincipal();
        handleAuthenticationSuccess(sessionUser, response);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(RestResponse.ofSuccess(null)));
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        handleAuthenticationFailure(response);
    }

    public void handleAuthenticationSuccess(SessionUser sessionUser, HttpServletResponse response) {
        String accessToken = sessionFactory.generateAccessToken(sessionUser);
        String refreshToken = sessionFactory.generateRefreshToken(sessionUser);

        Cookie accessTokenCookie = new Cookie(accessTokenCookieName, accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(secureCookie);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(sessionFactory.getAccessTokenExpirationSeconds());
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie(refreshTokenCookieName, refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(secureCookie);
        refreshTokenCookie.setPath(refreshUrl);
        refreshTokenCookie.setMaxAge(sessionFactory.getRefreshTokenExpirationSeconds());
        response.addCookie(refreshTokenCookie);

        redisRepository.save(
                sessionUser.getUsername(),
                refreshToken,
                Duration.ofSeconds(sessionFactory.getRefreshTokenExpirationSeconds()));
    }

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        handleAuthenticationFailure(response);
    }

    public void handleAuthenticationFailure(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        removeAccessTokenCookie(response);
        removeRefreshTokenCookie(response);
    }

    public void removeAccessTokenCookie(HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie(accessTokenCookieName, null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(secureCookie);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0);
        response.addCookie(accessTokenCookie);
    }

    public void removeRefreshTokenCookie(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie(refreshTokenCookieName, null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(secureCookie);
        refreshTokenCookie.setPath(refreshUrl);
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);
    }
}
