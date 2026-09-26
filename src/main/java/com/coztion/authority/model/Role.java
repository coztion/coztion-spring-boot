package com.coztion.authority.model;

import com.coztion.common.model.DataRecord;

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

    public RoleInfo toRoleInfo() {
        return RoleInfo.builder().roleCode(roleCode).roleName(roleName).build();
    }
}
