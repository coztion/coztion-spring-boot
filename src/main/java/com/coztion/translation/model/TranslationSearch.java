package com.coztion.translation.model;

import com.coztion.common.enums.DataStatus;
import com.coztion.common.model.Pagination;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationSearch extends Pagination {

    @Schema(description = "Translation Group")
    private String translationGroup;

    @Schema(description = "Translation Type")
    private String translationType;

    @Schema(description = "Translation Code")
    private String translationCode;

    @Schema(description = "Translation Locale")
    private String translationLocale;

    @Schema(description = "Data Status")
    private String dataStatus;

    @Schema(description = "Translation Key(group.type.code)")
    private String translationKey;

    public static TranslationSearch ofEnabled() {
        return TranslationSearch.builder()
                .dataStatus(DataStatus.ENABLED.getValue())
                .build();
    }
}
