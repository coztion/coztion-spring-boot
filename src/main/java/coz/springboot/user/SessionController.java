package coz.springboot.user;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.common.dto.RestResponse;
import coz.springboot.common.helper.SessionHolder;
import coz.springboot.common.security.AuthenticationHandler;
import coz.springboot.user.dto.SessionLoginRequest;

@Tag(name = "Session", description = "Session API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    private final AuthenticationHandler authenticationHandler;

    @Operation(summary = "Login", description = "Authenticate with username and password")
    @PostMapping("/login")
    public RestResponse<Void> login(@RequestBody SessionLoginRequest request) {
        return RestResponse.ofSuccess(null);
    }

    @Operation(summary = "Refresh Session", description = "Refresh access token using refresh token cookie")
    @PutMapping("/refresh")
    public RestResponse<Boolean> refreshSession() {
        return RestResponse.ofSuccess(true);
    }

    @Operation(summary = "Logout", description = "Invalidate session and remove token cookies")
    @DeleteMapping
    public RestResponse<Boolean> logout(HttpServletResponse response) {
        String username = SessionHolder.getUsername();
        sessionService.logout(username);
        authenticationHandler.removeRefreshTokenCookie(response);
        return RestResponse.ofSuccess(true);
    }
}
