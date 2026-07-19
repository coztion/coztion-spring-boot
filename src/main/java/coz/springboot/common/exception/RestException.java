package coz.springboot.common.exception;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import lombok.Getter;

@Getter
public class RestException extends RuntimeException {

    private final String messageKey;

    private final String[] labelKeys;

    public RestException(String messageKey) {
        this.messageKey = messageKey;
        this.labelKeys = null;
    }

    public RestException(String messageKey, String... labelKeys) {
        this.messageKey = messageKey;
        this.labelKeys = labelKeys;
    }

    public boolean hasMessageKey() {
        return StringUtils.hasText(messageKey);
    }

    public boolean hasLabelKeys() {
        return !ObjectUtils.isEmpty(labelKeys);
    }
}
