package com.coztion.code.model;

import java.util.List;

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
public class GroupCodesSearch extends Pagination {

    @Schema(description = "Group Codes")
    private List<String> groupCodes;

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Group Code Name")
    private String groupCodeName;

    @Schema(description = "Common Code Name")
    private String commonCodeName;

    @Schema(description = "Data Status")
    private String dataStatus;

    @Schema(description = "Include Deleted Data")
    private boolean includeDeletedData;
}
