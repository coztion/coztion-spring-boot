package coz.springboot.menu.dto;

import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.menu.model.MenuUpdate;

@Getter
@Setter
@NoArgsConstructor
public class MenuUpdateRequest {

    @Schema(description = "Menu Name")
    @Size(min = 1, max = 100)
    private String menuName;

    @Schema(description = "In Use")
    private Boolean isUse;

    public MenuUpdate toMenuUpdate(String menuId, String author) {
        return MenuUpdate.builder()
                .targetMenuId(menuId)
                .menuName(menuName)
                .isUse(isUse)
                .author(author)
                .build();
    }
}
