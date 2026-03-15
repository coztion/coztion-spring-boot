package coz.springboot.dto.code;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import coz.springboot.domain.code.CommonCode;
import coz.springboot.domain.code.GroupCode;
import coz.springboot.dto.common.DataRecordResponse;

@Getter
@SuperBuilder
public class GroupCodeGetResponse extends DataRecordResponse {

    @Schema(description = "그룹코드")
    private String groupCode;

    @Schema(description = "그룹코드명")
    private String groupCodeName;

    @Schema(description = "사용여부")
    private Boolean isUse;

    @Schema(description = "공통코드목록")
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
