package coz.springboot.translation.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.common.model.DataRecord;

@Getter
@Setter
@NoArgsConstructor
public class Translation extends DataRecord {

    @Schema(description = "Translation Group")
    private String translationGroup;

    @Schema(description = "Translation Type")
    private String translationType;

    @Schema(description = "Translation Code")
    private String translationCode;

    @Schema(description = "Translation Locale")
    private String translationLocale;

    @Schema(description = "Translation Content")
    private String translationContent;

    public TranslationInfo toTranslationInfo() {
        return TranslationInfo.builder()
                .translationGroup(translationGroup)
                .translationType(translationType)
                .translationCode(translationCode)
                .translationContent(translationContent)
                .build();
    }
}
