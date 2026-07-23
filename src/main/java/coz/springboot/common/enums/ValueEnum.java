package coz.springboot.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import org.springframework.util.StringUtils;

public interface ValueEnum {

    static <E extends Enum<E> & ValueEnum> E ofValue(Class<E> type, String value) {
        if (!StringUtils.hasText(value)) return null;

        for (E e : type.getEnumConstants()) {
            if (e.getValue().equals(value)) return e;
        }

        throw new IllegalArgumentException();
    }

    @JsonValue
    String getValue();
}
