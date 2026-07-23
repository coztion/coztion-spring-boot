package coz.springboot.common.exception;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import lombok.Getter;

@Getter
public class RestException extends RuntimeException {

    private final String messageCode;

    private final String[] labelCodes;

    public RestException(String messageCode) {
        this.messageCode = messageCode;
        this.labelCodes = null;
    }

    public RestException(String messageCode, String... labelCodes) {
        this.messageCode = messageCode;
        this.labelCodes = labelCodes;
    }

    public boolean hasMessageKey() {
        return StringUtils.hasText(messageCode);
    }

    public boolean hasLabelKeys() {
        return !ObjectUtils.isEmpty(labelCodes);
    }
}
