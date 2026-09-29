package com.coztion.authority.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PermissionInfo {

    @Schema(description = "Permission Code")
    private String permissionCode;

    @Schema(description = "Permission Name")
    private String permissionName;
}
