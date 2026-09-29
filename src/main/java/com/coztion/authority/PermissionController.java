package com.coztion.authority;

import java.util.List;

import com.coztion.authority.model.Permission;
import com.coztion.authority.model.PermissionCode;
import com.coztion.authority.model.PermissionInfo;
import com.coztion.authority.model.PermissionsSearch;
import com.coztion.common.dto.PageResponse;
import com.coztion.common.dto.RestResponse;
import com.coztion.core.security.Authorize;
import com.github.pagehelper.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@Tag(name = "Permission", description = "Permission API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @Operation(summary = "Get Permissions")
    @GetMapping
    @Authorize(permissionCodes = PermissionCode.AUTHORITY_MANAGEMENT_READ)
    public RestResponse<PageResponse<Permission>> getPermissions(@ModelAttribute PermissionsSearch permissionsSearch) {
        Page<Permission> permissions = permissionService.getPermissions(permissionsSearch);
        return RestResponse.ofSuccess(PageResponse.ofPage(permissions));
    }

    @Operation(summary = "Get Permission Infos")
    @GetMapping("/infos")
    public RestResponse<List<PermissionInfo>> getPermissionInfos() {
        Page<Permission> permissions = permissionService.getPermissions(PermissionsSearch.ofEnabled());
        List<PermissionInfo> permissionInfos =
                permissions.stream().map(Permission::toPermissionInfo).toList();
        return RestResponse.ofSuccess(permissionInfos);
    }
}
