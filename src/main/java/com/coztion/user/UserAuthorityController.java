package com.coztion.user;

import jakarta.validation.Valid;

import com.coztion.common.dto.PageResponse;
import com.coztion.common.dto.RestResponse;
import com.coztion.user.model.UserAuthoritiesSearch;
import com.coztion.user.model.UserAuthority;
import com.github.pagehelper.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@Tag(name = "User Authority", description = "User Authority API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-authorities")
public class UserAuthorityController {

    private final UserAuthorityService userAuthorityService;

    @Operation(summary = "Get User Authorities")
    @GetMapping
    public RestResponse<PageResponse<UserAuthority>> getUserAuthorities(
            @Valid @ModelAttribute UserAuthoritiesSearch userAuthoritiesSearch) {
        Page<UserAuthority> userAuthorities = userAuthorityService.getUserAuthorities(userAuthoritiesSearch);
        return RestResponse.ofSuccess(PageResponse.ofPage(userAuthorities));
    }
}
