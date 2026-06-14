package coz.springboot.menu.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import coz.springboot.common.enums.ValueEnum;

@Getter
@RequiredArgsConstructor
public enum MenuType implements ValueEnum {
    GROUP_NAVIGATION("GRP_NAV"),
    NAVIGATION("NAV"),
    PAGE("PAGE"),
    TAB("TAB");

    private final String value;
}
