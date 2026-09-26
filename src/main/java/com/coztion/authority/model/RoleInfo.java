package com.coztion.authority.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoleInfo {

    @Schema(description = "Role Code")
    private String roleCode;

    @Schema(description = "Role Name")
    private String roleName;
}
