package coz.springboot.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import coz.springboot.common.constant.Message;
import coz.springboot.common.dto.RestResponse;
import coz.springboot.common.translator.MessageTranslator;
import coz.springboot.common.translator.ValidationTranslator;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final MessageTranslator messageTranslator;

    private final ValidationTranslator validationTranslator;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<String>> handleValidationException(MethodArgumentNotValidException exception) {
        String errorMessage = validationTranslator.translate(
                exception.getBindingResult().getFieldErrors().getFirst());
        log.error("[VALIDATION] : {}", errorMessage);
        return ResponseEntity.ok().body(RestResponse.ofFailure(errorMessage));
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestResponse<String>> handleRestException(RestException exception) {
        String errorMessage = translateMessage(exception);
        log.error("[FAILURE] : {}", errorMessage);
        return ResponseEntity.ok(RestResponse.ofFailure(errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<String>> handleException(Exception exception) {
        log.error("[ERROR] : {}", exception.getMessage());

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
