package com.coztion.code.model;

import com.coztion.common.model.DataRecord;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonCode extends DataRecord {

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Common Code")
    private String commonCode;

    @Schema(description = "Common Code Name")
    private String commonCodeName;

    @Schema(description = "Common Code Description")
    private String commonCodeDescription;

    @Schema(description = "Sort Order")
    private Integer sortOrder;

    public Code toCode() {
        return Code.builder()
                .groupCode(groupCode)
                .commonCode(commonCode)
                .commonCodeName(commonCodeName)
                .build();
    }
}
