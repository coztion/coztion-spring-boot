package coz.springboot.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum YesOrNo implements ValueEnum {
    YES("Y"),
    NO("N");

    private final String value;

    public static YesOrNo ofValue(String value) {
        if (YES.value.equalsIgnoreCase(value)) {
            return YES;
        } else if (NO.value.equalsIgnoreCase(value)) {
            return NO;
        }
        return null;
    }

    public boolean isYes() {
        return this == YES;
    }
}
