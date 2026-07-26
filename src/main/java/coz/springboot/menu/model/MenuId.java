package coz.springboot.menu.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import coz.springboot.common.enums.ValueEnum;

@Getter
@RequiredArgsConstructor
public enum MenuId implements ValueEnum {
    MAIN("MAIN"),
    ADMIN("ADMIN"),
    CODE_MANAGEMENT("CODE_MANAGEMENT"),
    MENU_MANAGEMENT("MENU_MANAGEMENT"),
    TRANSLATION_MANAGEMENT("TRANSLATION_MANAGEMENT");

    private final String value;
}
