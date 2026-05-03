package coz.springboot.menu.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import coz.springboot.common.constant.Label;
import coz.springboot.common.enums.ValueEnum;

@Getter
@RequiredArgsConstructor
public enum MenuType implements ValueEnum {
    GROUP_NAVIGATION("GRP_NAV"),
    NAVIGATION("NAV"),
    PAGE("PAGE"),
    TAB("TAB");

    private final String value;

    public static MenuType ofValue(String value) {
        return ValueEnum.ofValue(MenuType.class, value, Label.Menu.MENU_TYPE);
    }

    @Override
    public String getValue() {
        return value;
    }
}
