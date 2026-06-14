package coz.springboot.code.domain;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import coz.springboot.common.enums.YesOrNo;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonCodeSearch {

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Group Codes")
    private List<String> groupCodes;

    @Schema(description = "Common Code")
    private String commonCode;

    @Schema(description = "In Use", example = "Y")
    private YesOrNo useYn;

    public static CommonCodeSearch ofGroupCodesInUse(List<String> groupCodes) {
        return CommonCodeSearch.builder()
                .groupCodes(groupCodes)
                .useYn(YesOrNo.YES)
                .build();
    }
}
