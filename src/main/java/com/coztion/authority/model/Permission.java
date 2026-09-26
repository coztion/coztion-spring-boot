package com.coztion.authority.model;

import com.coztion.common.model.DataRecord;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Permission extends DataRecord {

    @Schema(description = "Permission Code")
    private String permissionCode;

    @Schema(description = "Permission Name")
    private String permissionName;

    @Schema(description = "Sort Order")
    private Integer sortOrder;

    public PermissionInfo toPermissionInfo() {
        return PermissionInfo.builder()
                .permissionCode(permissionCode)
                .permissionName(permissionName)
                .build();
    }
}
