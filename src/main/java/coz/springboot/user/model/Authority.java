package coz.springboot.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import coz.springboot.global.constant.Label;
import coz.springboot.global.enums.ValueEnum;

@Getter
@RequiredArgsConstructor
public enum Authority implements ValueEnum {
    ADMIN("ROLE_ADMIN"),
    DEVELOPER("ROLE_DEVELOPER");

    private final String authorityCode;

    public static Authority ofValue(String value) {
        return ValueEnum.ofValue(Authority.class, value, Label.User.AUTHORITY);
    }

    @Override
    public String getValue() {
        return authorityCode;
    }
}
