package com.coztion.authority;

import java.util.List;

import jakarta.validation.Valid;

import com.coztion.authority.model.Permission;
import com.coztion.authority.model.PermissionCode;
import com.coztion.authority.model.PermissionDelete;
import com.coztion.authority.model.PermissionInfo;
import com.coztion.authority.model.PermissionOperation;
import com.coztion.authority.model.PermissionSave;
import com.coztion.authority.model.PermissionSearch;
import com.coztion.authority.model.PermissionUpdate;
import com.coztion.authority.model.PermissionsSearch;
import com.coztion.common.dto.PageResponse;
import com.coztion.common.dto.RestResponse;
import com.coztion.core.security.Authorize;
import com.github.pagehelper.Page;

import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Get Permission")
    @GetMapping("/{permissionCode}")
    @Authorize(permissionCodes = PermissionCode.AUTHORITY_MANAGEMENT_READ)
    public RestResponse<Permission> getPermission(@ModelAttribute PermissionSearch permissionSearch) {
        Permission foundPermission = permissionService.getPermission(permissionSearch);
        return RestResponse.ofSuccess(foundPermission);
    }

    @Operation(summary = "Post Permission")
    @PostMapping
    @Authorize(permissionCodes = PermissionCode.AUTHORITY_MANAGEMENT_WRITE)
    public RestResponse<Permission> postPermission(@Valid @RequestBody PermissionSave permissionSave) {
        Permission createdPermission = permissionService.savePermission(permissionSave);
        return RestResponse.ofSuccess(createdPermission);
    }

    @Operation(summary = "Put Permission")
    @PutMapping("/{permissionCode}")
    @Authorize(permissionCodes = PermissionCode.AUTHORITY_MANAGEMENT_WRITE)
    public RestResponse<Void> putPermission(@Valid @RequestBody PermissionUpdate permissionUpdate) {
        permissionService.updatePermission(permissionUpdate);
        return RestResponse.ofSuccess(null);
    }

    @Operation(summary = "Delete Permission")
    @DeleteMapping("/{permissionCode}")
    @Authorize(permissionCodes = PermissionCode.AUTHORITY_MANAGEMENT_WRITE)
    public RestResponse<Boolean> deletePermission(@Valid @RequestBody PermissionDelete permissionDelete) {
        permissionService.deletePermission(permissionDelete);
        return RestResponse.ofSuccess(true);
    }

    @Operation(summary = "Operate Permissions")
    @PostMapping("/operation")
    @Authorize(permissionCodes = PermissionCode.AUTHORITY_MANAGEMENT_WRITE)
    public RestResponse<Boolean> operationPermissions(@Valid @RequestBody PermissionOperation permissionOperation) {
        permissionService.operatePermissions(permissionOperation);
        return RestResponse.ofSuccess(true);
    }
}
