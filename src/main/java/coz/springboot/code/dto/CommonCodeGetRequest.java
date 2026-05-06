package coz.springboot.code.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.code.model.CommonCodeFilter;
import coz.springboot.common.enums.YesOrNo;

@Getter
@Setter
@NoArgsConstructor
public class CommonCodeGetRequest {

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Common Code")
    private String commonCode;

    @Schema(description = "In Use", example = "Y")
    private String useYn;

    public CommonCodeFilter toCommonCodeFilter() {
        return CommonCodeFilter.builder()
                .groupCode(groupCode)
                .commonCode(commonCode)
                .useYn(YesOrNo.ofValue(useYn))
                .build();
    }
}
