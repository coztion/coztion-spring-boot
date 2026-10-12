package com.coztion.user;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import com.coztion.authority.PermissionService;
import com.coztion.authority.model.Permission;
import com.coztion.authority.model.PermissionsSearch;
import com.coztion.common.dto.RestResponse;
import com.coztion.core.security.AuthenticationHandler;
import com.coztion.user.model.SessionUser;
import com.coztion.user.model.SessionUserInfo;
import com.coztion.user.model.UserProfileImage;
import com.coztion.user.model.UserProfileImageDelete;
import com.coztion.user.model.UserProfileImageUpdate;
import com.coztion.user.model.UserSearch;

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

    private final UserService userService;

    private final PermissionService permissionService;

    private final AuthenticationHandler authenticationHandler;

    @Operation(summary = "Get Session Info", description = "Get information about authenticated session user")
    @GetMapping("/user-info")
    public RestResponse<SessionUserInfo> getSessionUserInfo(@AuthenticationPrincipal SessionUser sessionUser) {
        List<String> permissionCodes =
                permissionService
                        .getPermissions(PermissionsSearch.ofEnabledRoleCode(sessionUser.getRoleCode()))
                        .stream()
                        .map(Permission::getPermissionCode)
                        .toList();
        UserProfileImage userProfileImage =
                userService.getUserProfileImage(UserSearch.ofUsername(sessionUser.getUsername()));
        SessionUserInfo sessionUserInfo = sessionUser.toPublicInfo(permissionCodes, userProfileImage);
        return RestResponse.ofSuccess(sessionUserInfo);
    }

    @Operation(summary = "Put Session Profile Image", description = "Set profile image of authenticated session user")
    @PutMapping("/profile-image")
    public RestResponse<Void> putProfileImage(@Valid @RequestBody UserProfileImageUpdate userProfileImageUpdate) {
        userService.updateUserProfileImage(userProfileImageUpdate);
        return RestResponse.ofSuccess(null);
    }

    @Operation(
            summary = "Delete Session Profile Image",
            description = "Remove profile image of authenticated session user")
    @DeleteMapping("/profile-image")
    public RestResponse<Boolean> deleteProfileImage(@ModelAttribute UserProfileImageDelete userProfileImageDelete) {
        userService.deleteUserProfileImage(userProfileImageDelete);
        return RestResponse.ofSuccess(true);
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
