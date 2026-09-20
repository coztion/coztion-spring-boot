package com.coztion.user;

import jakarta.validation.Valid;

import com.coztion.common.dto.PageResponse;
import com.coztion.common.dto.RestResponse;
import com.coztion.core.security.Authorize;
import com.coztion.menu.model.MenuId;
import com.coztion.user.model.User;
import com.coztion.user.model.UserSearch;
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
    @Authorize(menuIds = MenuId.USER_MANAGEMENT)
    public RestResponse<PageResponse<User>> getUsers(@Valid @ModelAttribute UsersSearch usersSearch) {
        Page<User> users = userService.getUsers(usersSearch);
        return RestResponse.ofSuccess(PageResponse.ofPage(users));
    }

    @Operation(summary = "Get User")
    @GetMapping("/{username}")
    @Authorize(menuIds = MenuId.USER_MANAGEMENT)
    public RestResponse<User> getUser(@ModelAttribute UserSearch userSearch) {
        User foundUser = userService.getUserOrThrow(userSearch);
        return RestResponse.ofSuccess(foundUser);
    }
}
