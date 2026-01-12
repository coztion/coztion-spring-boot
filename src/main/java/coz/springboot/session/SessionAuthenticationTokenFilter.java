package coz.springboot.session;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

import coz.springboot.core.user.vo.SessionUser;
import coz.springboot.helper.RedisRepository;
import coz.springboot.helper.SessionFactory;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionAuthenticationTokenFilter extends OncePerRequestFilter {

    private final SessionFactory sessionFactory;

    private final SessionAuthenticationHandler sessionAuthenticationHandler;

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
                sessionAuthenticationHandler.handleAuthenticationFailure(response);
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
                sessionAuthenticationHandler.handleAuthenticationFailure(response);
                return;
            }

            sessionAuthenticationHandler.handleAuthenticationSuccess(sessionUser, response);

            Authentication authentication =
                    UsernamePasswordAuthenticationToken.authenticated(sessionUser, null, sessionUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            log.warn("Invalid or expired refresh token: {}", e.getMessage());
            sessionAuthenticationHandler.handleAuthenticationFailure(response);
        }
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) return null;

        for (Cookie cookie : cookies) {
            if (SessionAuthenticationHandler.REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
