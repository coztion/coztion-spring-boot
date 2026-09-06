package com.coztion.user;

import jakarta.validation.Valid;

import com.coztion.common.dto.PageResponse;
import com.coztion.common.dto.RestResponse;
import com.coztion.user.model.User;
import com.coztion.user.model.UsersSearch;
import com.github.pagehelper.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public RestResponse<PageResponse<User>> getUsers(@Valid @ModelAttribute UsersSearch usersSearch) {
        Page<User> users = userService.getUsers(usersSearch);
        return RestResponse.ofSuccess(PageResponse.ofPage(users));
    }
}
