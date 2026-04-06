package coz.springboot.menu.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import coz.springboot.common.model.PageFilter;

@Getter
@SuperBuilder
public class MenuFilter extends PageFilter {

    private MenuType menuType;

    private Boolean isUse;
}
