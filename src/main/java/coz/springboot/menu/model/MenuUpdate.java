package coz.springboot.menu.model;

import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.common.constant.TranslationKey;
import coz.springboot.common.enums.DataStatus;

@Getter
@Setter
@NoArgsConstructor
public class MenuUpdate {

    @Schema(description = "Target Menu ID", hidden = true)
    private String targetMenuId;

    @Schema(description = "Menu Name")
    @Size(min = 1, max = 100, message = TranslationKey.Menu.Label.MENU_NAME)
    private String menuName;

    @Schema(description = "Data Status")
    private DataStatus dataStatus;

    @Schema(description = "Author", hidden = true)
    private String author;
}
