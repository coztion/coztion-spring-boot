package com.coztion.authority;

import java.util.List;

import jakarta.validation.Valid;

import com.coztion.authority.model.PermissionCode;
import com.coztion.authority.model.Role;
import com.coztion.authority.model.RoleDelete;
import com.coztion.authority.model.RoleInfo;
import com.coztion.authority.model.RoleOperation;
import com.coztion.authority.model.RoleSave;
import com.coztion.authority.model.RoleSearch;
import com.coztion.authority.model.RoleUpdate;
import com.coztion.authority.model.RolesSearch;
import com.coztion.common.dto.PageResponse;
import com.coztion.common.dto.RestResponse;
import com.coztion.core.security.Authorize;
import com.github.pagehelper.Page;

import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Get Role")
    @GetMapping("/{roleCode}")
    @Authorize(permissionCodes = PermissionCode.AUTHORITY_MANAGEMENT_READ)
    public RestResponse<Role> getRole(@ModelAttribute RoleSearch roleSearch) {
        Role foundRole = roleService.getRole(roleSearch);
        return RestResponse.ofSuccess(foundRole);
    }

    @Operation(summary = "Post Role")
    @PostMapping
    @Authorize(permissionCodes = PermissionCode.AUTHORITY_MANAGEMENT_WRITE)
    public RestResponse<Role> postRole(@Valid @RequestBody RoleSave roleSave) {
        Role createdRole = roleService.saveRole(roleSave);
        return RestResponse.ofSuccess(createdRole);
    }

    @Operation(summary = "Put Role")
    @PutMapping("/{roleCode}")
    @Authorize(permissionCodes = PermissionCode.AUTHORITY_MANAGEMENT_WRITE)
    public RestResponse<Void> putRole(@Valid @RequestBody RoleUpdate roleUpdate) {
        roleService.updateRole(roleUpdate);
        return RestResponse.ofSuccess(null);
    }

    @Operation(summary = "Delete Role")
    @DeleteMapping("/{roleCode}")
    @Authorize(permissionCodes = PermissionCode.AUTHORITY_MANAGEMENT_WRITE)
    public RestResponse<Boolean> deleteRole(@Valid @RequestBody RoleDelete roleDelete) {
        roleService.deleteRole(roleDelete);
        return RestResponse.ofSuccess(true);
    }

    @Operation(summary = "Operate Roles")
    @PostMapping("/operation")
    @Authorize(permissionCodes = PermissionCode.AUTHORITY_MANAGEMENT_WRITE)
    public RestResponse<Boolean> operationRoles(@Valid @RequestBody RoleOperation roleOperation) {
        roleService.operateRoles(roleOperation);
        return RestResponse.ofSuccess(true);
    }
}
