package coz.springboot.user;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.common.dto.RestResponse;
import coz.springboot.common.helper.SessionHolder;
import coz.springboot.common.security.AuthenticationHandler;
import coz.springboot.user.domain.SessionUser;
import coz.springboot.user.domain.SessionUserInfo;

@Tag(name = "Session", description = "Session API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    private final AuthenticationHandler authenticationHandler;

    @Operation(summary = "Get Session Info", description = "Get information about authenticated session user")
    @GetMapping("/user-info")
    public RestResponse<SessionUserInfo> getSessionUserInfo() {
        SessionUser sessionUser = SessionHolder.getSessionUser();
        SessionUserInfo sessionUserInfo = sessionUser.toPublicInfo();
        return RestResponse.ofSuccess(sessionUserInfo);
    }

    @Operation(
            summary = "Refresh Session",
            description = "Reissue access and refresh tokens using a valid refresh token")
    @PutMapping("/refresh")
    public RestResponse<Void> refresh() {
        return RestResponse.ofSuccess(null);
    }

    @Operation(summary = "Logout", description = "Invalidate session and remove token cookies")
    @DeleteMapping
    public RestResponse<Boolean> logout(HttpServletResponse response) {
        String username = SessionHolder.getUsername();
        sessionService.logout(username);
        authenticationHandler.removeAccessTokenCookie(response);
        authenticationHandler.removeRefreshTokenCookie(response);
        return RestResponse.ofSuccess(true);
    }
}
