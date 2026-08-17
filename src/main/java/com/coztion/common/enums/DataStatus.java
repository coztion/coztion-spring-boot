package com.coztion.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DataStatus implements ValueEnum {
    ENABLED("ENABLED"),
    DISABLED("DISABLED");

    private final String value;

    public static DataStatus ofValue(String value) {
        return ValueEnum.ofValue(DataStatus.class, value);
    }

    public boolean isEnabled() {
        return this.equals(ENABLED);
    }
}
