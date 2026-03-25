package coz.springboot.dto.code;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import coz.springboot.global.dto.response.DataRecordResponse;
import coz.springboot.vo.code.CommonCode;
import coz.springboot.vo.code.GroupCode;

@Getter
@SuperBuilder
public class GroupCodeGetResponse extends DataRecordResponse {

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Group Code Name")
    private String groupCodeName;

    @Schema(description = "In Use")
    private Boolean isUse;

    @Schema(description = "Common Code List")
    private List<CommonCode> commonCodes;

    public static GroupCodeGetResponse ofDomain(GroupCode groupCode) {
        return GroupCodeGetResponse.builder()
                .groupCode(groupCode.getGroupCode())
                .groupCodeName(groupCode.getGroupCodeName())
                .isUse(groupCode.getIsUse())
                .commonCodes(groupCode.getCommonCodes())
                .dataRecord(groupCode)
                .build();
    }
}
