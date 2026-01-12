package coz.springboot.session;

import java.util.Map;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

import coz.springboot.core.user.vo.SessionUser;

@Component
public class SessionAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final SessionAuthenticationHandler sessionAuthenticationHandler;

    public SessionAuthenticationFilter(
            AuthenticationManager authenticationManager, SessionAuthenticationHandler sessionAuthenticationHandler) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.sessionAuthenticationHandler = sessionAuthenticationHandler;
        setFilterProcessesUrl("/login");
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        var authenticationRequest = mapper.readValue(request.getInputStream(), Map.class);
        String username = (String) authenticationRequest.get("username");
        String password = (String) authenticationRequest.get("password");
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        return authenticationManager.authenticate(authentication);
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication) {
        SessionUser sessionUser = (SessionUser) authentication.getPrincipal();
        sessionAuthenticationHandler.handleAuthenticationSuccess(sessionUser, response);
    }
}
