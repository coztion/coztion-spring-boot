package coz.springboot.helper;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.crypto.SecretKey;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import lombok.Getter;

import coz.springboot.domain.user.SessionUser;

@Getter
@Component
public class SessionFactory {

    @Value("${spring.security.secret.token}")
    private String tokenSecret;

    @Value("${spring.security.expiration.access-token}")
    private long accessTokenExpirationMillis;

    @Value("${spring.security.expiration.refresh-token}")
    private long refreshTokenExpirationMillis;

    public SecretKey secretKey;

    @PostConstruct
    public void initializeSecretKey() {
        secretKey = Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(SessionUser sessionUser) {
        return generateToken(sessionUser, accessTokenExpirationMillis);
    }

    public String generateRefreshToken(SessionUser sessionUser) {
        return generateToken(sessionUser, refreshTokenExpirationMillis);
    }

    public SessionUser validateAccessToken(String accessToken) {
        return validateToken(accessToken);
    }

    public SessionUser validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken);
    }

    public int getRefreshTokenExpirationSeconds() {
        return (int) (refreshTokenExpirationMillis / 1000);
    }

    private String generateToken(SessionUser sessionUser, long expirationMillis) {
        Map<String, Object> claims =
                Map.of("username", sessionUser.getUsername(), "authorities", sessionUser.getAuthorityCodes());

        Instant now = Instant.now();
        Instant expiration = now.plusMillis(expirationMillis);

        Date generateDate = Date.from(now);
        Date expirationDate = Date.from(expiration);

        return Jwts.builder()
                .claims(claims)
                .subject(sessionUser.getUsername())
                .issuedAt(generateDate)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    private SessionUser validateToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String username = claims.getSubject();

        List<?> authorities = claims.get("authorities", List.class);
        List<String> authorityCodes = Objects.nonNull(authorities)
                ? authorities.stream().map(Object::toString).toList()
                : List.of();

        return SessionUser.builder()
                .username(username)
                .authorityCodes(authorityCodes)
                .build();
    }
}
