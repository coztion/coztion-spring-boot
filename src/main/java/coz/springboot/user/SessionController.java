package coz.springboot.user;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import coz.springboot.global.dto.RestResponse;
import coz.springboot.global.helper.SessionHolder;
import coz.springboot.global.security.AuthenticationHandler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    private final AuthenticationHandler authenticationHandler;

    @PutMapping("/refresh")
    public RestResponse<Boolean> refreshSession() {
        return RestResponse.ofSuccess(true);
    }

    @DeleteMapping
    public RestResponse<Boolean> logout(HttpServletResponse response) {
        String username = SessionHolder.getUsername();
        sessionService.logout(username);
        authenticationHandler.removeRefreshTokenCookie(response);
        return RestResponse.ofSuccess(true);
    }
}
