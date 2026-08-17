package com.coztion.menu.model;

import com.coztion.common.enums.ValueEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuId implements ValueEnum {
    MAIN("MAIN"),
    ADMIN("ADMIN"),
    CODE_MANAGEMENT("CODE_MANAGEMENT"),
    MENU_MANAGEMENT("MENU_MANAGEMENT"),
    TRANSLATION_MANAGEMENT("TRANSLATION_MANAGEMENT"),
    DEVELOPMENT("DEV"),
    DEV_FOUNDATION("DEV_FOUNDATION"),
    DEV_BUTTON("DEV_BUTTON"),
    DEV_COMPONENT("DEV_COMPONENT");

    private final String value;
}
