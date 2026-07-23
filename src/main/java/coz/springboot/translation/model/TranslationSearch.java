package coz.springboot.translation.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import coz.springboot.common.enums.DataStatus;
import coz.springboot.common.model.Pagination;

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
    private DataStatus dataStatus;
}
