package coz.springboot.core.user.controller;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import coz.springboot.dto.RestResponse;
import coz.springboot.helper.RedisRepository;
import coz.springboot.helper.SessionHolder;
import coz.springboot.session.SessionAuthenticationHandler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {

    private final SessionAuthenticationHandler sessionAuthenticationHandler;

    private final RedisRepository redisRepository;

    @GetMapping
    public RestResponse<String> getSession() {
        String username = SessionHolder.getUsername();
        return RestResponse.ofSuccess(username);
    }

    @PutMapping("/refresh")
    public RestResponse<Boolean> refreshSession() {
        return RestResponse.ofSuccess(true);
    }

    @DeleteMapping
    public RestResponse<Boolean> logout(HttpServletResponse response) {
        String username = SessionHolder.getUsername();
        redisRepository.remove(username);
        sessionAuthenticationHandler.removeRefreshTokenCookie(response);
        return RestResponse.ofSuccess(true);
    }
}
