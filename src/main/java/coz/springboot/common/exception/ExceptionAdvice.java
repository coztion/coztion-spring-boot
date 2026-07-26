package coz.springboot.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import coz.springboot.common.constant.TranslationKey;
import coz.springboot.common.dto.RestResponse;
import coz.springboot.common.dto.RestResponse.Failure;
import coz.springboot.common.translator.ValidationTranslator;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ValidationTranslator validationTranslator;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Void>> handleValidationException(MethodArgumentNotValidException exception) {
        Failure failure = validationTranslator.translate(
                exception.getBindingResult().getFieldErrors().getFirst());
        log.error("[VALIDATION] : {}", failure.getFailureMessageKey());
        return ResponseEntity.ok().body(RestResponse.ofFailure(failure));
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestResponse<Void>> handleRestException(RestException exception) {
        log.error("[FAILURE] : {}", exception.getTranslationMessageKey());

        return ResponseEntity.ok()
                .body(RestResponse.ofFailure(
                        exception.getTranslationMessageKey(), exception.getTranslationLabelKeys()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestResponse<Void>> handleAccessDeniedException(AccessDeniedException exception) {
        log.error("[FORBIDDEN] : {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(RestResponse.ofFailure(TranslationKey.User.Message.AUTHORIZATION_FAILURE));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Void>> handleException(Exception exception) {
        log.error("[ERROR] : {}", exception.getMessage());

        return ResponseEntity.internalServerError()
                .body(RestResponse.ofFailure(TranslationKey.Common.Message.INTERNAL_SERVER_ERROR_FAILURE));
    }
}
