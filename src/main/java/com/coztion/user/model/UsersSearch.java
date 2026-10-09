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

    @Schema(description = "Role Code")
    private String roleCode;

    @Schema(description = "Data Status")
    private String dataStatus;

    public static UsersSearch ofRoleCode(String roleCode) {
        return UsersSearch.builder().roleCode(roleCode).build();
    }
}
