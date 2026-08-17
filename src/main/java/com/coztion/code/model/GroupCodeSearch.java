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

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Data Status")
    private DataStatus dataStatus;

    @Schema(description = "Include Deleted Data")
    private boolean includeDeletedData;

    public static GroupCodeSearch ofGroupCode(String groupCode) {
        return GroupCodeSearch.builder().groupCode(groupCode).build();
    }

    public static GroupCodeSearch ofGroupCodeIncludingDeletedData(String groupCode) {
        return GroupCodeSearch.builder()
                .groupCode(groupCode)
                .includeDeletedData(true)
                .build();
    }
}
