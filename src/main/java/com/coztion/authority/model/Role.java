package com.coztion.authority.model;

import java.util.List;

import com.coztion.common.model.DataRecord;

import org.springframework.util.CollectionUtils;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Role extends DataRecord {

    @Schema(description = "Role Code")
    private String roleCode;

    @Schema(description = "Role Name")
    private String roleName;

    @Schema(description = "Sort Order")
    private Integer sortOrder;

    @Schema(description = "Permission Codes")
    private List<String> permissionCodes;

    public boolean hasPermissionCodes() {
        return !CollectionUtils.isEmpty(permissionCodes);
    }

    public RoleInfo toRoleInfo() {
        return RoleInfo.builder().roleCode(roleCode).roleName(roleName).build();
    }
}
