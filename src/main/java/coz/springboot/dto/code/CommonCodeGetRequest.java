package coz.springboot.dto.code;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.domain.code.CommonCodeFilter;

@Getter
@Setter
@NoArgsConstructor
public class CommonCodeGetRequest {

    @Schema(description = "그룹코드")
    private String groupCode;

    @Schema(description = "공통코드")
    private String commonCode;

    @Schema(description = "사용여부", example = "true")
    private Boolean isUse;

    public CommonCodeFilter toDomain() {
        return CommonCodeFilter.builder()
                .groupCode(groupCode)
                .commonCode(commonCode)
                .isUse(isUse)
                .build();
    }
}
