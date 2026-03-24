package coz.springboot.global.security;

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

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import coz.springboot.global.helper.RedisRepository;
import coz.springboot.global.helper.SessionFactory;
import coz.springboot.vo.user.SessionUser;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${spring.security.refresh-token-cookie-name}")
    private String refreshTokenCookieName;

    private final SessionFactory sessionFactory;

    private final AuthenticationHandler authenticationHandler;

    private final RedisRepository redisRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Optional<String> optionalAccessToken = extractAuthorizationHeader(request);

        if (optionalAccessToken.isPresent()) {
            try {
                String accessToken = optionalAccessToken.get();
                SessionUser sessionUser = sessionFactory.validateAccessToken(accessToken);
                Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
                        sessionUser, null, sessionUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException e) {
                log.warn(
                        "Access token has been expired for user : {}",
                        e.getClaims().getSubject());
                handleTokenRefresh(request, response);
            } catch (JwtException e) {
                log.warn("Access token is invalid : {}", e.getMessage());
                authenticationHandler.handleAuthenticationFailure(response);
            }
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractAuthorizationHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return Optional.of(authorizationHeader.substring(7));
        }
        return Optional.empty();
    }

    private void handleTokenRefresh(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refreshToken = extractRefreshTokenFromCookie(request);
            if (!StringUtils.hasText(refreshToken)) return;

            SessionUser sessionUser = sessionFactory.validateRefreshToken(refreshToken);

            String cachedRefreshToken = redisRepository.get(sessionUser.getUsername(), String.class);
            if (Objects.isNull(cachedRefreshToken)) {
                log.warn("No refresh token found in Redis for user: {}", sessionUser.getUsername());
                authenticationHandler.handleAuthenticationFailure(response);
                return;
            }

            if (!cachedRefreshToken.equals(refreshToken)) {
                log.warn("Refresh token mismatch for user: {}.", sessionUser.getUsername());
                redisRepository.remove(sessionUser.getUsername());
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
        }
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
