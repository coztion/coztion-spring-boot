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
public class RolesSearch extends Pagination {

    @Schema(description = "Data Status", example = "ENABLED")
    private String dataStatus;

    public static RolesSearch ofEnabled() {
        return RolesSearch.builder().dataStatus(DataStatus.ENABLED.getValue()).build();
    }
}
