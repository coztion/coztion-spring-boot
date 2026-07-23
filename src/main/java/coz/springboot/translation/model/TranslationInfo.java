package coz.springboot.translation.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TranslationInfo {

    @Schema(description = "Translation Group")
    private String translationGroup;

    @Schema(description = "Translation Type")
    private String translationType;

    @Schema(description = "Translation Code")
    private String translationCode;

    @Schema(description = "Translation Content")
    private String translationContent;

    @SuppressWarnings("unused")
    @Schema(description = "Translation Key(group.type.code)")
    public String getTranslationKey() {
        return translationGroup + "." + translationType + "." + translationCode;
    }
}
