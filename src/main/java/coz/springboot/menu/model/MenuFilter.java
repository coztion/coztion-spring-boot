package coz.springboot.menu.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuFilter {

    private MenuType menuType;

    private Boolean isUse;
}
