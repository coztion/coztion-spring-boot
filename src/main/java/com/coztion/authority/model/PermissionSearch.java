package com.coztion.authority.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionSearch {

    @Schema(description = "Permission Code")
    private String permissionCode;

    public static PermissionSearch ofPermissionCode(String permissionCode) {
        return PermissionSearch.builder().permissionCode(permissionCode).build();
    }
}
