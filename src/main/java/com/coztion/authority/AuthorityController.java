package com.coztion.authority;

import java.util.List;

import com.coztion.authority.model.AuthoritiesSearch;
import com.coztion.authority.model.Authority;
import com.coztion.authority.model.AuthorityInfo;
import com.coztion.common.dto.PageResponse;
import com.coztion.common.dto.RestResponse;
import com.coztion.core.security.Authorize;
import com.coztion.menu.model.MenuId;
import com.github.pagehelper.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@Tag(name = "Authority", description = "Authority API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/authorities")
public class AuthorityController {

    private final AuthorityService authorityService;

    @Operation(summary = "Get Authorities")
    @GetMapping
    @Authorize(menuIds = MenuId.AUTHORITY_MANAGEMENT)
    public RestResponse<PageResponse<Authority>> getAuthorities(@ModelAttribute AuthoritiesSearch authoritiesSearch) {
        Page<Authority> authorities = authorityService.getAuthorities(authoritiesSearch);
        return RestResponse.ofSuccess(PageResponse.ofPage(authorities));
    }

    @Operation(summary = "Get Authority Infos")
    @GetMapping("/infos")
    public RestResponse<List<AuthorityInfo>> getAuthorityInfos() {
        Page<Authority> authorities = authorityService.getAuthorities(AuthoritiesSearch.ofEnabled());
        List<AuthorityInfo> authorityInfos =
                authorities.stream().map(Authority::toAuthorityInfo).toList();
        return RestResponse.ofSuccess(authorityInfos);
    }
}
