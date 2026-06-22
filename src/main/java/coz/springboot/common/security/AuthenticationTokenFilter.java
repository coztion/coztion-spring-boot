package coz.springboot.common.security;

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

import coz.springboot.common.helper.RedisRepository;
import coz.springboot.common.helper.SessionFactory;
import coz.springboot.user.domain.SessionUser;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${spring.security.cookie.access-token}")
    private String accessTokenCookieName;

    @Value("${spring.security.cookie.refresh-token}")
    private String refreshTokenCookieName;

    private final SessionFactory sessionFactory;

    private final AuthenticationHandler authenticationHandler;

    private final RedisRepository redisRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return "/session/login".equals(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Optional<String> optionalAccessToken = extractAccessTokenFromCookie(request);

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
                if (!attemptRefresh(request, response)) return;
            } catch (JwtException e) {
                log.warn("Access token is invalid : {}", e.getMessage());
                authenticationHandler.handleAuthenticationFailure(response);
                return;
            }
        } else {
            boolean failedRefresh = !attemptRefresh(request, response);
            if (failedRefresh) return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean attemptRefresh(HttpServletRequest request, HttpServletResponse response) {
        boolean refreshHandled = handleTokenRefresh(request, response);
        if (!refreshHandled) {
            log.warn("No refresh token found in request");
            authenticationHandler.handleAuthenticationFailure(response);
            return false;
        }
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    private Optional<String> extractAccessTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) return Optional.empty();

        for (Cookie cookie : cookies) {
            if (accessTokenCookieName.equals(cookie.getName())) {
                return Optional.of(cookie.getValue());
            }
        }
        return Optional.empty();
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

    private boolean handleTokenRefresh(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refreshToken = extractRefreshTokenFromCookie(request);
            if (!StringUtils.hasText(refreshToken)) return false;

            SessionUser sessionUser = sessionFactory.validateRefreshToken(refreshToken);

            Optional<String> cachedRefreshToken = redisRepository.find(sessionUser.getUsername());
            if (cachedRefreshToken.isEmpty()) {
                log.warn("No refresh token found in Redis for user: {}", sessionUser.getUsername());
                authenticationHandler.handleAuthenticationFailure(response);
                return true;
            }

            if (!cachedRefreshToken.get().equals(refreshToken)) {
                log.warn("Refresh token mismatch for user: {}", sessionUser.getUsername());
                authenticationHandler.handleAuthenticationFailure(response);
                return true;
            }

            authenticationHandler.handleAuthenticationSuccess(sessionUser, response);

            Authentication authentication =
                    UsernamePasswordAuthenticationToken.authenticated(sessionUser, null, sessionUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            log.warn("Invalid or expired refresh token: {}", e.getMessage());
            authenticationHandler.handleAuthenticationFailure(response);
        }

        return true;
    }
}
