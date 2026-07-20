package coz.springboot.core.security;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import tools.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    public AuthenticationFilter(
            AuthenticationManager authenticationManager,
            AuthenticationHandler authenticationHandler,
            ObjectMapper objectMapper,
            @Value("${spring.security.url.login}") String loginUrl) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        setFilterProcessesUrl(loginUrl);
        setAuthenticationSuccessHandler(authenticationHandler);
        setAuthenticationFailureHandler(authenticationHandler);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        var authenticationRequest = objectMapper.readValue(request.getInputStream(), Map.class);
        String username = (String) authenticationRequest.get("username");
        String password = (String) authenticationRequest.get("password");
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        return getAuthenticationManager().authenticate(authentication);
    }
}
