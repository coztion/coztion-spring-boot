package coz.springboot.code.domain;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import coz.springboot.common.domain.Pagination;
import coz.springboot.common.enums.YesOrNo;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CommonCodeSearch extends Pagination {

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
