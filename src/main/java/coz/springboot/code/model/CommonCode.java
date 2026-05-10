package coz.springboot.code.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.common.enums.YesOrNo;
import coz.springboot.common.model.DataRecord;

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

    @Schema(description = "Usage Status")
    private YesOrNo useYn = YesOrNo.YES;

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
