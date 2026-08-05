package com.coztion.code.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.coztion.common.constant.TranslationKey;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonCodeSave {

    @Schema(description = "Group Code")
    @NotBlank(message = TranslationKey.Code.Label.GROUP_CODE)
    private String groupCode;

    @Schema(description = "Common Code")
    @NotBlank(message = TranslationKey.Code.Label.COMMON_CODE)
    private String commonCode;

    @Schema(description = "Common Code Name")
    @NotBlank(message = TranslationKey.Code.Label.COMMON_CODE_NAME)
    private String commonCodeName;

    @Schema(description = "Common Code Description")
    private String commonCodeDescription;

    @Schema(description = "Data Status")
    @NotBlank(message = TranslationKey.Common.Label.DATA_STATUS)
    private String dataStatus;

    @Schema(description = "Sort Order")
    @NotNull(message = TranslationKey.Common.Label.SORT_ORDER)
    @Min(value = 1, message = TranslationKey.Common.Label.SORT_ORDER)
    private Integer sortOrder;

    @Schema(description = "Author", hidden = true)
    private String author;
}
