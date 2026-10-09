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
public class RoleSearch {

    @Schema(description = "Role Code")
    private String roleCode;

    public static RoleSearch ofRoleCode(String roleCode) {
        return RoleSearch.builder().roleCode(roleCode).build();
    }
}
