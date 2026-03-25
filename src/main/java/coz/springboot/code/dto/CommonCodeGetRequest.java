package coz.springboot.code.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.code.domain.CommonCodeFilter;

@Getter
@Setter
@NoArgsConstructor
public class CommonCodeGetRequest {

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Common Code")
    private String commonCode;

    @Schema(description = "In Use", example = "true")
    private Boolean isUse;

    public CommonCodeFilter toDomain() {
        return CommonCodeFilter.builder()
                .groupCode(groupCode)
                .commonCode(commonCode)
                .isUse(isUse)
                .build();
    }
}
