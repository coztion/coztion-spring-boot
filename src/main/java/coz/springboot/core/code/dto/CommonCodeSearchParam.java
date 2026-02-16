package coz.springboot.core.code.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonCodeSearchParam {

    @Schema(description = "그룹코드")
    private String groupCode;

    @Schema(description = "공통코드")
    private String commonCode;

    @Schema(description = "사용여부", example = "true")
    private Boolean isUse;
}
