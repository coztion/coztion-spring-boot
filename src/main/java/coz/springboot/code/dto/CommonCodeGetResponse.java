package coz.springboot.code.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import coz.springboot.code.model.CommonCode;
import coz.springboot.common.dto.DataRecordResponse;

@Getter
@SuperBuilder
public class CommonCodeGetResponse extends DataRecordResponse {

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Common Code")
    private String commonCode;

    @Schema(description = "Common Code Name")
    private String commonCodeName;

    @Schema(description = "Usage Status")
    private String useYn;

    public static CommonCodeGetResponse fromCommonCode(CommonCode commonCode) {
        return CommonCodeGetResponse.builder()
                .groupCode(commonCode.getGroupCode())
                .commonCode(commonCode.getCommonCode())
                .commonCodeName(commonCode.getCommonCodeName())
                .useYn(commonCode.getUseYn().getValue())
                .dataRecord(commonCode)
                .build();
    }
}
