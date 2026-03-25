package coz.springboot.menu.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuFilter {

    private MenuType menuType;

    private Boolean isUse;
}
