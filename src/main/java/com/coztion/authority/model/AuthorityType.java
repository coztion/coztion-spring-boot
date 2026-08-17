package com.coztion.authority.model;

import com.coztion.common.enums.ValueEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthorityType implements ValueEnum {
    READ("READ"),
    WRITE("WRITE");

    private final String value;

    public static AuthorityType ofValue(String value) {
        return ValueEnum.ofValue(AuthorityType.class, value);
    }
}
