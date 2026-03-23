package coz.springboot.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import coz.springboot.constant.Label;
import coz.springboot.enums.common.ValueEnum;

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
