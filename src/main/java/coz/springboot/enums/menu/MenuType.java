package coz.springboot.enums.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import coz.springboot.enums.common.ValueEnum;
import coz.springboot.global.constant.Label;

@Getter
@RequiredArgsConstructor
public enum MenuType implements ValueEnum {
    GROUP_NAVIGATION("GRP_NAV"),
    NAVIGATION("NAV"),
    URL("URL"),
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
