package coz.springboot.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import coz.springboot.enums.common.ValueEnum;

@Getter
@RequiredArgsConstructor
public enum Authority implements ValueEnum {
    ADMIN("ROLE_ADMIN"),
    DEVELOPER("ROLE_DEVELOPER");

    private final String authorityCode;

    @Override
    public String getValue() {
        return authorityCode;
    }
}
