package coz.springboot.dto.menu;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.domain.menu.MenuFilter;
import coz.springboot.enums.menu.MenuType;

@Getter
@Setter
@NoArgsConstructor
public class MenuGetRequest {

    @Schema(description = "Menu Type", example = "GRP_NAV")
    private String menuType;

    @Schema(description = "Usage Status", example = "true")
    private Boolean isUse;

    public MenuFilter toDomain() {
        return MenuFilter.builder()
                .menuType(MenuType.ofValue(menuType))
                .isUse(isUse)
                .build();
    }
}
