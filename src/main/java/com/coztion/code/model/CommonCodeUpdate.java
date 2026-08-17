package com.coztion.code.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.coztion.common.constant.TranslationKey;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonCodeUpdate {

    @Schema(description = "Group Code")
    @NotBlank(message = TranslationKey.Code.Label.GROUP_CODE)
    private String targetGroupCode;

    @Schema(description = "Target Common Code", hidden = true)
    private String targetCommonCode;

    @Schema(description = "Common Code Name")
    @NotBlank(message = TranslationKey.Code.Label.COMMON_CODE_NAME)
    @Size(min = 1, max = 100, message = TranslationKey.Code.Label.COMMON_CODE_NAME)
    private String commonCodeName;

    @Schema(description = "Common Code Description")
    private String commonCodeDescription;

    @Schema(description = "Data Status", example = "ENABLED")
    @NotNull(message = TranslationKey.Common.Label.DATA_STATUS)
    private String dataStatus;

    @Schema(description = "Author", hidden = true)
    private String author;
}
