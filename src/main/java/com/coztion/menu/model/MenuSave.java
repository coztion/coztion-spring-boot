package com.coztion.menu.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.coztion.common.constant.TranslationKey;
import com.coztion.common.model.OperationAuthor;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuSave extends OperationAuthor {

    @Schema(description = "Menu ID")
    @NotBlank(message = TranslationKey.Menu.Label.MENU_ID)
    private String menuId;

    @Schema(description = "Menu Name")
    @NotBlank(message = TranslationKey.Menu.Label.MENU_NAME)
    @Size(min = 1, max = 100, message = TranslationKey.Menu.Label.MENU_NAME)
    private String menuName;

    @Schema(description = "Menu Type")
    @NotBlank(message = TranslationKey.Menu.Label.MENU_TYPE)
    private String menuType;

    @Schema(description = "URL")
    @NotBlank(message = TranslationKey.Menu.Label.URL)
    private String url;

    @Schema(description = "Parent Menu ID")
    private String parentMenuId;

    @Schema(description = "Sort Order")
    @NotNull(message = TranslationKey.Common.Label.SORT_ORDER)
    @Min(value = 1, message = TranslationKey.Common.Label.SORT_ORDER)
    private Integer sortOrder;

    @Schema(description = "Data Status")
    @NotBlank(message = TranslationKey.Common.Label.DATA_STATUS)
    private String dataStatus;
}
