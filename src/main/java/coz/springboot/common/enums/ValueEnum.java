package coz.springboot.common.enums;

import org.springframework.util.StringUtils;

import coz.springboot.common.constant.Message;
import coz.springboot.common.exception.ClientException;

public interface ValueEnum {

    static <E extends Enum<E> & ValueEnum> E ofValue(Class<E> type, String value, String label) {
        if (!StringUtils.hasText(value)) return null;

        for (E e : type.getEnumConstants()) {
            if (e.getValue().equals(value)) return e;
        }

        throw new ClientException(Message.Exception.BAD_REQUEST, label);
    }

    String getValue();
}
