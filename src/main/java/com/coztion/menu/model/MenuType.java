package com.coztion.menu.model;

import com.coztion.common.enums.ValueEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuType implements ValueEnum {
    GROUP_NAVIGATION("GRP_NAV"),
    NAVIGATION("NAV"),
    PAGE("PAGE"),
    TAB("TAB");

    private final String value;
}
