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
        return ResponseEntity.ok().body(RestResponse.ofFailureMessage(errorMessage));
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestResponse<String>> handleRestException(RestException exception) {
        boolean hasExistingMessageCode = messageTranslator.hasExistingMessageCode(exception.getMessageCode());
        if (hasExistingMessageCode) {
            final String failureMessage = translateMessage(exception);
            log.error("[FAILURE] : {}", failureMessage);
            return ResponseEntity.ok().body(RestResponse.ofFailureMessage(failureMessage));
        }

        log.error("[FAILURE] : {}", exception.getMessageCode());
        return ResponseEntity.ok(RestResponse.ofFailureCode(exception.getMessageCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Void>> handleException(Exception exception) {
        log.error("[ERROR] : {}", exception.getMessage());

        return ResponseEntity.internalServerError()
                .body(RestResponse.ofFailureCode(Message.Common.Failure.INTERNAL_SERVER_ERROR_FAILURE));
    }

    private String translateMessage(RestException exception) {
        if (exception.hasMessageKey() && exception.hasLabelKeys()) {
            return messageTranslator.translate(exception.getMessageCode(), exception.getLabelCodes());
        }

        if (exception.hasMessageKey()) {
            return messageTranslator.translate(exception.getMessageCode());
        }

        return exception.getMessageCode();
    }
}
