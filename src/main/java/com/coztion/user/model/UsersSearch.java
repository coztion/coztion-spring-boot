package com.coztion.user.model;

import com.coztion.common.model.Pagination;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UsersSearch extends Pagination {

    @Schema(description = "Username")
    private String username;

    @Schema(description = "Authority Role")
    private String authorityRole;

    @Schema(description = "Data Status")
    private String dataStatus;

    @Schema(description = "Include Authority Roles in Result")
    private boolean includeAuthorityRoles;
}
