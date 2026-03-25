package coz.springboot.global.exception;

public class BusinessException extends RestException {

    public BusinessException(String messageKey) {
        super(messageKey);
    }

    public BusinessException(String messageKey, String... labelKeys) {
        super(messageKey, labelKeys);
    }
}
