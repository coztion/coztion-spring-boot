package coz.springboot.code.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import coz.springboot.code.model.CommonCode;
import coz.springboot.code.model.GroupCode;
import coz.springboot.common.dto.DataRecordResponse;

@Getter
@SuperBuilder
public class GroupCodeGetResponse extends DataRecordResponse {

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Group Code Name")
    private String groupCodeName;

    @Schema(description = "In Use")
    private String useYn;

    @Schema(description = "Common Code List")
    private List<CommonCode> commonCodes;

    public static GroupCodeGetResponse fromGroupCode(GroupCode groupCode) {
        return GroupCodeGetResponse.builder()
                .groupCode(groupCode.getGroupCode())
                .groupCodeName(groupCode.getGroupCodeName())
                .useYn(groupCode.getUseYn().getValue())
                .commonCodes(groupCode.getCommonCodes())
                .dataRecord(groupCode)
                .build();
    }
}
