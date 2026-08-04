package com.coztion.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum YesOrNo implements ValueEnum {
    YES("Y"),
    NO("N");

    private final String value;
}
