package coz.springboot.common.exception;

import lombok.Getter;

@Getter
public class RestException extends RuntimeException {

    private final String translationMessageKey;

    private final String[] translationLabelKeys;

    public RestException(String translationMessageKey, String... translationLabelKeys) {
        this.translationMessageKey = translationMessageKey;
        this.translationLabelKeys = translationLabelKeys;
    }
}
