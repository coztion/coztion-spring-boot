package coz.springboot.exception;

public class ClientException extends RestException {

    public ClientException(String messageKey) {
        super(messageKey);
    }

    public ClientException(String messageKey, String... labelKeys) {
        super(messageKey, labelKeys);
    }
}
