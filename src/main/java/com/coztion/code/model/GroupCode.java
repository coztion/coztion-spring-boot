package com.coztion.code.model;

import java.util.List;

import com.coztion.common.model.DataRecord;

import org.springframework.util.CollectionUtils;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupCode extends DataRecord {

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Group Code Name")
    private String groupCodeName;

    @Schema(description = "Group Code Description")
    private String groupCodeDescription;

    @Schema(description = "Sort Order")
    private Integer sortOrder;

    @Schema(description = "Common Code List")
    private List<CommonCode> commonCodes;

    public boolean hasEnabledCommonCodes() {
        if (CollectionUtils.isEmpty(commonCodes)) {
            return false;
        }
        return commonCodes.stream().anyMatch(DataRecord::isEnabled);
    }

    public boolean hasCommonCodes() {
        return !CollectionUtils.isEmpty(commonCodes);
    }
}
