package coz.springboot.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import coz.springboot.common.constant.Message;
import coz.springboot.common.dto.RestResponse;
import coz.springboot.common.helper.MessageTranslator;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final MessageTranslator messageTranslator;

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestResponse<String>> handleRestException(RestException exception) {
        String errorMessage = translateMessage(exception);
        log.error("Rest Exception : {}", errorMessage);
        return ResponseEntity.internalServerError().body(RestResponse.ofFailure(errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<String>> handleException(Exception exception) {
        log.error("Unhandled Exception : {}", exception.getMessage());
        log.error("Exception Log : ", exception);

        String errorMessage = messageTranslator.translate(Message.Exception.INTERNAL_SERVER_ERROR);
        return ResponseEntity.internalServerError().body(RestResponse.ofFailure(errorMessage));
    }

    private String translateMessage(RestException exception) {
        if (exception.hasMessageKey() && exception.hasLabelKeys()) {
            return messageTranslator.translate(exception.getMessageKey(), exception.getLabelKeys());
        }

        if (exception.hasMessageKey()) {
            return messageTranslator.translate(exception.getMessageKey());
        }

        return exception.getMessageKey();
    }
}
