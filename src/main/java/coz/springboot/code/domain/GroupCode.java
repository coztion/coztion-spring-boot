package coz.springboot.code.domain;

import java.util.List;

import org.springframework.util.CollectionUtils;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.common.domain.DataRecord;

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

    public boolean hasCommonCodes() {
        return !CollectionUtils.isEmpty(commonCodes);
    }
}
