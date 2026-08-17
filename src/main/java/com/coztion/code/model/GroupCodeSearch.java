package com.coztion.code.model;

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
public class GroupCodeSearch {

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Data Status")
    private String dataStatus;

    public static GroupCodeSearch ofGroupCode(String groupCode) {
        return GroupCodeSearch.builder().groupCode(groupCode).build();
    }
}
