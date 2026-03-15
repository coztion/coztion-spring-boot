package coz.springboot.domain.menu;

import lombok.Builder;
import lombok.Getter;

import coz.springboot.enums.menu.MenuType;

@Getter
@Builder
public class MenuFilter {

    private MenuType menuType;

    private Boolean isUse;
}
