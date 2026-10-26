package com.coztion.core.security;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationHandler authenticationHandler;

    private final ObjectMapper objectMapper;

    private final String loginUrl;

    public AuthenticationFilter(
            AuthenticationManager authenticationManager,
            AuthenticationHandler authenticationHandler,
            ObjectMapper objectMapper,
            @Value("${spring.security.url.login}") String loginUrl) {
        super(authenticationManager);
        this.authenticationHandler = authenticationHandler;
        this.objectMapper = objectMapper;
        this.loginUrl = loginUrl;
        setFilterProcessesUrl(loginUrl);
        setAuthenticationSuccessHandler(authenticationHandler);
        setAuthenticationFailureHandler(authenticationHandler);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (loginUrl.equals(httpServletRequest.getRequestURI())
                && !HttpMethod.POST.matches(httpServletRequest.getMethod())) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }
        super.doFilter(request, response, chain);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Map<?, ?> authenticationRequest;
        try {
            authenticationRequest = objectMapper.readValue(request.getInputStream(), Map.class);
        } catch (JacksonException exception) {
            log.error("[VALIDATION] : {}", exception.getMessage());
            authenticationHandler.handleRequestValidationFailure(response);
            return null;
        }

        if (authenticationRequest == null
                || !isNullableString(authenticationRequest.get("username"))
                || !isNullableString(authenticationRequest.get("password"))) {
            log.error("[VALIDATION] : Invalid login request");
            authenticationHandler.handleRequestValidationFailure(response);
            return null;
        }

        String username = (String) authenticationRequest.get("username");
        String password = (String) authenticationRequest.get("password");
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        return getAuthenticationManager().authenticate(authentication);
    }

    private boolean isNullableString(Object value) {
        return value == null || value instanceof String;
    }
}
