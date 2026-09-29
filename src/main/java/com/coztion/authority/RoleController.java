package com.coztion.authority;

import java.util.List;

import com.coztion.authority.model.PermissionCode;
import com.coztion.authority.model.Role;
import com.coztion.authority.model.RoleInfo;
import com.coztion.authority.model.RolesSearch;
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

@Tag(name = "Role", description = "Role API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Get Roles")
    @GetMapping
    @Authorize(permissionCodes = PermissionCode.AUTHORITY_MANAGEMENT_READ)
    public RestResponse<PageResponse<Role>> getRoles(@ModelAttribute RolesSearch rolesSearch) {
        Page<Role> roles = roleService.getRoles(rolesSearch);
        return RestResponse.ofSuccess(PageResponse.ofPage(roles));
    }

    @Operation(summary = "Get Role Infos")
    @GetMapping("/infos")
    public RestResponse<List<RoleInfo>> getRoleInfos() {
        Page<Role> roles = roleService.getRoles(RolesSearch.ofEnabled());
        List<RoleInfo> roleInfos = roles.stream().map(Role::toRoleInfo).toList();
        return RestResponse.ofSuccess(roleInfos);
    }
}
