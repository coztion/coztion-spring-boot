package com.coztion.authority.model;

import com.coztion.common.enums.DataStatus;
import com.coztion.common.model.Pagination;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PermissionsSearch extends Pagination {

    @Schema(description = "Role Code", example = "ROLE_ADMIN")
    private String roleCode;

    @Schema(description = "Data Status", example = "ENABLED")
    private String dataStatus;

    public static PermissionsSearch ofEnabled() {
        return PermissionsSearch.builder()
                .dataStatus(DataStatus.ENABLED.getValue())
                .build();
    }

    public static PermissionsSearch ofEnabledRoleCode(String roleCode) {
        return PermissionsSearch.builder()
                .roleCode(roleCode)
                .dataStatus(DataStatus.ENABLED.getValue())
                .build();
    }
}
