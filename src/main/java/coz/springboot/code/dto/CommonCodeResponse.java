package coz.springboot.code.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import coz.springboot.code.domain.CommonCode;
import coz.springboot.global.dto.response.DataRecordResponse;

@Getter
@SuperBuilder
public class CommonCodeResponse extends DataRecordResponse {

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Common Code")
    private String commonCode;

    @Schema(description = "Common Code Name")
    private String commonCodeName;

    @Schema(description = "Usage Status")
    private Boolean isUse;

    public static CommonCodeResponse ofDomain(CommonCode commonCode) {
        return CommonCodeResponse.builder()
                .groupCode(commonCode.getGroupCode())
                .commonCode(commonCode.getCommonCode())
                .commonCodeName(commonCode.getCommonCodeName())
                .isUse(commonCode.getIsUse())
                .dataRecord(commonCode)
                .build();
    }
}
