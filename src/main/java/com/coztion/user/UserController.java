package com.coztion.user;

import jakarta.validation.Valid;

import com.coztion.authority.model.PermissionCode;
import com.coztion.common.dto.PageResponse;
import com.coztion.common.dto.RestResponse;
import com.coztion.core.security.Authorize;
import com.coztion.user.model.User;
import com.coztion.user.model.UserDelete;
import com.coztion.user.model.UserOperation;
import com.coztion.user.model.UserPasswordReset;
import com.coztion.user.model.UserSave;
import com.coztion.user.model.UserSearch;
import com.coztion.user.model.UserUpdate;
import com.coztion.user.model.UsersSearch;
import com.github.pagehelper.Page;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@Tag(name = "User", description = "User API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get Users")
    @GetMapping
    @Authorize(permissionCodes = PermissionCode.USER_MANAGEMENT_READ)
    public RestResponse<PageResponse<User>> getUsers(@Valid @ModelAttribute UsersSearch usersSearch) {
        Page<User> users = userService.getUsers(usersSearch);
        return RestResponse.ofSuccess(PageResponse.ofPage(users));
    }

    @Operation(summary = "Get User")
    @GetMapping("/{username}")
    @Authorize(permissionCodes = PermissionCode.USER_MANAGEMENT_READ)
    public RestResponse<User> getUser(@ModelAttribute UserSearch userSearch) {
        User foundUser = userService.getUser(userSearch);
        return RestResponse.ofSuccess(foundUser);
    }

    @Operation(summary = "Post User")
    @PostMapping
    @Authorize(permissionCodes = PermissionCode.USER_MANAGEMENT_WRITE)
    public RestResponse<User> postUser(@Valid @RequestBody UserSave userSave) {
        User createdUser = userService.saveUser(userSave);
        return RestResponse.ofSuccess(createdUser);
    }

    @Operation(summary = "Put User")
    @PutMapping("/{username}")
    @Authorize(permissionCodes = PermissionCode.USER_MANAGEMENT_WRITE)
    public RestResponse<Void> putUser(@Valid @RequestBody UserUpdate userUpdate) {
        userService.updateUser(userUpdate);
        return RestResponse.ofSuccess(null);
    }

    @Operation(summary = "Reset User Password")
    @PostMapping("/password/reset")
    @Authorize(permissionCodes = PermissionCode.USER_MANAGEMENT_WRITE)
    public RestResponse<Boolean> resetUserPassword(@Valid @RequestBody UserPasswordReset userPasswordReset) {
        userService.resetUserPassword(userPasswordReset);
        return RestResponse.ofSuccess(true);
    }

    @Operation(summary = "Delete User")
    @DeleteMapping("/{username}")
    @Authorize(permissionCodes = PermissionCode.USER_MANAGEMENT_WRITE)
    public RestResponse<Boolean> deleteUser(@Valid @RequestBody UserDelete userDelete) {
        userService.deleteUser(userDelete);
        return RestResponse.ofSuccess(true);
    }

    @Operation(summary = "Operate Users")
    @PostMapping("/operation")
    @Authorize(permissionCodes = PermissionCode.USER_MANAGEMENT_WRITE)
    public RestResponse<Boolean> operationUsers(@Valid @RequestBody UserOperation userOperation) {
        userService.operateUsers(userOperation);
        return RestResponse.ofSuccess(true);
    }
}
