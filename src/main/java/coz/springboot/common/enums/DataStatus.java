package coz.springboot.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DataStatus implements ValueEnum {
    ENABLED("ENABLED"),
    DISABLED("DISABLED"),
    DELETED("DELETED");

    private final String value;
}
