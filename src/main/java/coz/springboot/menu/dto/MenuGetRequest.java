package coz.springboot.menu.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.menu.model.MenuFilter;
import coz.springboot.menu.model.MenuType;

@Getter
@Setter
@NoArgsConstructor
public class MenuGetRequest {

    @Schema(description = "Menu Type", example = "GRP_NAV")
    private String menuType;

    @Schema(description = "Usage Status", example = "true")
    private Boolean isUse;

    public MenuFilter toMenuFilter() {
        return MenuFilter.builder()
                .menuType(MenuType.ofValue(menuType))
                .isUse(isUse)
                .build();
    }
}
