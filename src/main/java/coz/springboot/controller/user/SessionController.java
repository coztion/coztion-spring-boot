package coz.springboot.controller.user;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import coz.springboot.dto.common.RestResponse;
import coz.springboot.helper.SessionHolder;
import coz.springboot.service.user.SessionService;
import coz.springboot.session.SessionAuthenticationHandler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    private final SessionAuthenticationHandler sessionAuthenticationHandler;

    @PutMapping("/refresh")
    public RestResponse<Boolean> refreshSession() {
        return RestResponse.ofSuccess(true);
    }

    @DeleteMapping
    public RestResponse<Boolean> logout(HttpServletResponse response) {
        String username = SessionHolder.getUsername();
        sessionService.logout(username);
        sessionAuthenticationHandler.removeRefreshTokenCookie(response);
        return RestResponse.ofSuccess(true);
    }
}
