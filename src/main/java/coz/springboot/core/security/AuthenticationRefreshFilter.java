package coz.springboot.core.security;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import coz.springboot.common.redis.RedisRepository;
import coz.springboot.user.domain.SessionUser;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationRefreshFilter extends OncePerRequestFilter {

    @Value("${spring.security.cookie.refresh-token}")
    private String refreshTokenCookieName;

    @Value("${spring.security.url.refresh}")
    private String refreshUrl;

    private final AuthenticationFactory authenticationFactory;

    private final AuthenticationHandler authenticationHandler;

    private final RedisRepository redisRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !(refreshUrl.equals(request.getRequestURI()) && "PUT".equals(request.getMethod()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String refreshToken = extractRefreshTokenFromCookie(request);
        if (!StringUtils.hasText(refreshToken)) {
            authenticationHandler.handleAuthenticationFailure(response);
            return;
        }

        try {
            SessionUser sessionUser = authenticationFactory.validateRefreshToken(refreshToken);

            Optional<String> cachedRefreshToken = redisRepository.findAndDelete(sessionUser.getUsername());
            if (cachedRefreshToken.isEmpty()) {
                log.warn("No refresh token found in Redis for user: {}", sessionUser.getUsername());
                authenticationHandler.handleAuthenticationFailure(response);
                return;
            }

            if (!cachedRefreshToken.get().equals(refreshToken)) {
                log.warn("Refresh token mismatch for user: {}", sessionUser.getUsername());
                authenticationHandler.handleAuthenticationFailure(response);
                return;
            }

            authenticationHandler.handleAuthenticationSuccess(sessionUser, response);

            Authentication authentication =
                    UsernamePasswordAuthenticationToken.authenticated(sessionUser, null, sessionUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            log.warn("Invalid or expired refresh token: {}", e.getMessage());
            authenticationHandler.handleAuthenticationFailure(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) return null;

        for (Cookie cookie : cookies) {
            if (refreshTokenCookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
