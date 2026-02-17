package coz.springboot.domain.code;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.domain.common.DataRecord;

@Getter
@Setter
@NoArgsConstructor
public class CommonCode extends DataRecord {

    @Schema(description = "그룹코드")
    private String groupCode;

    @Schema(description = "공통코드")
    private String commonCode;

    @Schema(description = "공통코드명")
    private String commonCodeName;

    @Schema(description = "사용여부")
    private Boolean isUse;
}
