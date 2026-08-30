package com.coztion.translation.model;

import jakarta.validation.constraints.NotBlank;

import com.coztion.common.constant.TranslationKey;
import com.coztion.common.model.OperationAuthor;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationDelete extends OperationAuthor {

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
}
