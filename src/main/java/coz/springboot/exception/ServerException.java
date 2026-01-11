package coz.springboot.exception;

public class ServerException extends RestException {

    public ServerException(String messageKeyCode) {
        super(messageKeyCode);
    }

    public ServerException(String messageKey, String... labelKeys) {
        super(messageKey, labelKeys);
    }
}
