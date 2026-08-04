package com.coztion.code.model;

import com.coztion.common.enums.DataStatus;

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

    @Schema(description = "Group Code", hidden = true)
    private String groupCode;

    @Schema(description = "Data Status")
    private DataStatus dataStatus;

    public static GroupCodeSearch ofGroupCode(String groupCode) {
        return GroupCodeSearch.builder().groupCode(groupCode).build();
    }
}
