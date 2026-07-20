package coz.springboot.core.security;

import java.io.IOException;
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

import coz.springboot.common.cache.RedisRepository;
import coz.springboot.common.dto.RestResponse;
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

    private final AuthenticationFactory authenticationFactory;

    private final RedisRepository redisRepository;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SessionUser sessionUser = (SessionUser) authentication.getPrincipal();
        handleAuthenticationSuccess(sessionUser, response);
        writeJson(response, RestResponse.ofSuccess(null));
    }

    @SneakyThrows
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        handleAuthenticationFailure(response);
        writeJson(response, RestResponse.ofFailure(exception.getMessage()));
    }

    public void handleAuthenticationSuccess(SessionUser sessionUser, HttpServletResponse response) {
        String accessToken = authenticationFactory.generateAccessToken(sessionUser);
        String refreshToken = authenticationFactory.generateRefreshToken(sessionUser);

        Cookie accessTokenCookie = new Cookie(accessTokenCookieName, accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(secureCookie);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(authenticationFactory.getAccessTokenExpirationSeconds());
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie(refreshTokenCookieName, refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(secureCookie);
        refreshTokenCookie.setPath(refreshUrl);
        refreshTokenCookie.setMaxAge(authenticationFactory.getRefreshTokenExpirationSeconds());
        response.addCookie(refreshTokenCookie);

        redisRepository.save(
                sessionUser.getUsername(),
                refreshToken,
                Duration.ofSeconds(authenticationFactory.getRefreshTokenExpirationSeconds()));
    }

    @SneakyThrows
    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        handleAuthenticationFailure(response);
        writeJson(response, RestResponse.ofFailure(authException.getMessage()));
    }

    private void writeJson(HttpServletResponse response, RestResponse<?> body) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(body));
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
