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
public class CommonCodeSearch {

    @Schema(description = "Group Code", hidden = true)
    private String groupCode;

    @Schema(description = "Common Code", hidden = true)
    private String commonCode;

    @Schema(description = "Data Status")
    private DataStatus dataStatus;

    public static CommonCodeSearch ofCode(String groupCode, String commonCode) {
        return CommonCodeSearch.builder()
                .groupCode(groupCode)
                .commonCode(commonCode)
                .build();
    }
}
