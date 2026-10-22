package com.coztion.core.security;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import tools.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    private final String loginUrl;

    public AuthenticationFilter(
            AuthenticationManager authenticationManager,
            AuthenticationHandler authenticationHandler,
            ObjectMapper objectMapper,
            @Value("${spring.security.url.login}") String loginUrl) {
        super(authenticationManager);
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
        var authenticationRequest = objectMapper.readValue(request.getInputStream(), Map.class);
        String username = (String) authenticationRequest.get("username");
        String password = (String) authenticationRequest.get("password");
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        return getAuthenticationManager().authenticate(authentication);
    }
}
