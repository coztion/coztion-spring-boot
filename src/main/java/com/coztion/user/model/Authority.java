package com.coztion.user.model;

import com.coztion.common.enums.ValueEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority implements ValueEnum {
    SYSTEM("ROLE_SYSTEM"),
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String authorityRole;

    public static Authority ofValue(String value) {
        return ValueEnum.ofValue(Authority.class, value);
    }

    @Override
    public String getValue() {
        return authorityRole;
    }
}
