package com.coztion.menu.model;

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
public class MenuUpdate {

    @Schema(description = "Target Menu ID", hidden = true)
    private String targetMenuId;

    @Schema(description = "Menu Name")
    @NotBlank(message = TranslationKey.Menu.Label.MENU_NAME)
    @Size(min = 1, max = 100, message = TranslationKey.Menu.Label.MENU_NAME)
    private String menuName;

    @Schema(description = "Data Status", example = "ENABLED")
    @NotNull(message = TranslationKey.Common.Label.DATA_STATUS)
    private String dataStatus;

    @Schema(description = "Author", hidden = true)
    private String author;
}
