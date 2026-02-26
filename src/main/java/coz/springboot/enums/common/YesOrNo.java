package coz.springboot.enums.common;

import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum YesOrNo {
    YES("Y"),
    NO("N");

    private final String value;

    public static YesOrNo of(String value) {
        if (YES.value.equalsIgnoreCase(value)) {
            return YES;
        } else if (NO.value.equalsIgnoreCase(value)) {
            return NO;
        }
        return null;
    }

    public static YesOrNo ofBoolean(Boolean value) {
        if (Objects.isNull(value)) {
            return null;
        }

        return value ? YES : NO;
    }

    public boolean isYes() {
        return this == YES;
    }
}
