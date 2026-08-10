package com.coztion.user.model;

import com.coztion.common.enums.ValueEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthorityRole implements ValueEnum {
    SYSTEM("ROLE_SYSTEM"),
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String authorityRole;

    public static AuthorityRole ofValue(String value) {
        return ValueEnum.ofValue(AuthorityRole.class, value);
    }

    @Override
    public String getValue() {
        return authorityRole;
    }
}
