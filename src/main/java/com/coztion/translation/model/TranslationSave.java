package com.coztion.translation.model;

import jakarta.validation.constraints.NotBlank;

import com.coztion.common.constant.TranslationKey;
import com.coztion.common.model.OperationAuthor;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TranslationSave extends OperationAuthor {

    @Schema(description = "Translation Group")
    @NotBlank(message = TranslationKey.Translation.Label.TRANSLATION_GROUP)
    private String translationGroup;

    @Schema(description = "Translation Type")
    @NotBlank(message = TranslationKey.Translation.Label.TRANSLATION_TYPE)
    private String translationType;

    @Schema(description = "Translation Code")
    @NotBlank(message = TranslationKey.Translation.Label.TRANSLATION_CODE)
    private String translationCode;

    @Schema(description = "Translation Locale")
    @NotBlank(message = TranslationKey.Translation.Label.TRANSLATION_LOCALE)
    private String translationLocale;

    @Schema(description = "Translation Content")
    @NotBlank(message = TranslationKey.Translation.Label.TRANSLATION_CONTENT)
    private String translationContent;

    @Schema(description = "Data Status")
    @NotBlank(message = TranslationKey.Common.Label.DATA_STATUS)
    private String dataStatus;
}
