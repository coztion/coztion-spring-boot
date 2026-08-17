package com.coztion.code.model;

import java.util.List;

import com.coztion.common.enums.DataStatus;
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
public class CommonCodesSearch extends Pagination {

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Group Codes")
    private List<String> groupCodes;

    @Schema(description = "Common Code")
    private String commonCode;

    @Schema(description = "Data Status", example = "ENABLED")
    private String dataStatus;

    @Schema(description = "Include Deleted Data")
    private boolean includeDeletedData;

    public static CommonCodesSearch ofEnabledGroupCodes(List<String> groupCodes) {
        return CommonCodesSearch.builder()
                .groupCodes(groupCodes)
                .dataStatus(DataStatus.ENABLED.getValue())
                .build();
    }
}
