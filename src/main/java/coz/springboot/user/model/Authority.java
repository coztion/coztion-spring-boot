package coz.springboot.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import coz.springboot.common.constant.Label;
import coz.springboot.common.enums.ValueEnum;

@Getter
@RequiredArgsConstructor
public enum Authority implements ValueEnum {
    ADMIN("ROLE_ADMIN"),
    DEVELOPER("ROLE_DEVELOPER"),
    USER("ROLE_USER");

    private final String authorityCode;

    public static Authority ofValue(String value) {
        return ValueEnum.ofValue(Authority.class, value, Label.User.AUTHORITY);
    }

    @Override
    public String getValue() {
        return authorityCode;
    }
}
