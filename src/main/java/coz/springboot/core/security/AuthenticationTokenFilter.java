package coz.springboot.core.security;

import java.io.IOException;
import java.util.Arrays;
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
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import coz.springboot.user.model.SessionUser;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Value("${spring.security.cookie.access-token}")
    private String accessTokenCookieName;

    @Value("${spring.security.url.login}")
    private String loginUrl;

    @Value("${spring.security.url.refresh}")
    private String refreshUrl;

    @Value("${spring.security.url.unauthenticated}")
    private String[] unauthenticatedUrls;

    private final AuthenticationFactory authenticationFactory;

    private final AuthenticationHandler authenticationHandler;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return loginUrl.equals(uri)
                || (refreshUrl.equals(uri) && "PUT".equals(request.getMethod()))
                || Arrays.stream(unauthenticatedUrls).anyMatch(pattern -> PATH_MATCHER.match(pattern, uri));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Optional<String> optionalAccessToken = extractAccessTokenFromCookie(request);

        if (optionalAccessToken.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            String accessToken = optionalAccessToken.get();
            SessionUser sessionUser = authenticationFactory.validateAccessToken(accessToken);
            Authentication authentication =
                    UsernamePasswordAuthenticationToken.authenticated(sessionUser, null, sessionUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            log.warn(
                    "Access token has been expired for user : {}", e.getClaims().getSubject());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (JwtException e) {
            log.warn("Access token is invalid : {}", e.getMessage());
            authenticationHandler.handleAuthenticationFailure(response);
            return;
        }

        filterChain.doFilter(request, response);
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
}
