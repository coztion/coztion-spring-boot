package coz.springboot.core.code.vo;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;

import coz.springboot.core.shared.vo.DataRecord;

@Data
@EqualsAndHashCode(callSuper = true)
public class GroupCode extends DataRecord {

    @Schema(description = "그룹코드")
    private String groupCode;

    @Schema(description = "그룹코드명")
    private String groupCodeName;

    @Schema(description = "사용여부")
    private Boolean isUse;

    @Schema(description = "공통코드목록")
    private List<CommonCode> commonCodes;
}
