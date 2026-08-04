package com.coztion.user;

import jakarta.servlet.http.HttpServletResponse;

import com.coztion.common.dto.RestResponse;
import com.coztion.core.security.AuthenticationHandler;
import com.coztion.user.model.SessionUser;
import com.coztion.user.model.SessionUserInfo;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@Tag(name = "Session", description = "Session API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    private final AuthenticationHandler authenticationHandler;

    @Operation(summary = "Get Session Info", description = "Get information about authenticated session user")
    @GetMapping("/user-info")
    public RestResponse<SessionUserInfo> getSessionUserInfo(@AuthenticationPrincipal SessionUser sessionUser) {
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
    public RestResponse<Boolean> logout(
            @AuthenticationPrincipal SessionUser sessionUser, HttpServletResponse response) {
        sessionService.logout(sessionUser.getUsername());
        authenticationHandler.removeAccessTokenCookie(response);
        authenticationHandler.removeRefreshTokenCookie(response);
        return RestResponse.ofSuccess(true);
    }
}
