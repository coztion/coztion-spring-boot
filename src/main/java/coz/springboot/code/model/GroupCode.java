package coz.springboot.code.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.common.enums.YesOrNo;
import coz.springboot.common.model.DataRecord;

@Getter
@Setter
@NoArgsConstructor
public class GroupCode extends DataRecord {

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Group Code Name")
    private String groupCodeName;

    @Schema(description = "In Use")
    private YesOrNo useYn = YesOrNo.YES;

    @Schema(description = "Sort Order")
    private Integer sortOrder;

    @Schema(description = "Common Code List")
    private List<CommonCode> commonCodes;
}
