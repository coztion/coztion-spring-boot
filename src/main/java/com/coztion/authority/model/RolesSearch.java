package com.coztion.authority.model;

import java.util.List;

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
public class RolesSearch extends Pagination {

    @Schema(description = "Role Name")
    private String roleName;

    @Schema(description = "Permission Codes")
    private List<String> permissionCodes;

    @Schema(description = "Data Status", example = "ENABLED")
    private String dataStatus;

    public static RolesSearch ofEnabled() {
        return RolesSearch.builder().dataStatus(DataStatus.ENABLED.getValue()).build();
    }
}
