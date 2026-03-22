package coz.springboot.session;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

@Component
public class SessionAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public SessionAuthenticationFilter(
            AuthenticationManager authenticationManager, SessionAuthenticationHandler sessionAuthenticationHandler) {
        super(authenticationManager);
        setFilterProcessesUrl("/login");
        setAuthenticationSuccessHandler(sessionAuthenticationHandler);
        setAuthenticationFailureHandler(sessionAuthenticationHandler);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        var authenticationRequest = mapper.readValue(request.getInputStream(), Map.class);
        String username = (String) authenticationRequest.get("username");
        String password = (String) authenticationRequest.get("password");
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        return getAuthenticationManager().authenticate(authentication);
    }
}
