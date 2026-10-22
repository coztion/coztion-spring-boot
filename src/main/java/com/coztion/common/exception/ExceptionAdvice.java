package com.coztion.common.exception;

import com.coztion.common.constant.TranslationKey;
import com.coztion.common.dto.RestResponse;
import com.coztion.common.dto.RestResponse.Failure;
import com.coztion.common.translator.ValidationTranslator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    @ExceptionHandler({
        MissingServletRequestParameterException.class,
        HttpMessageNotReadableException.class,
        MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<RestResponse<Void>> handleRequestException(Exception exception) {
        log.error("[VALIDATION] : {}", exception.getMessage());

        return ResponseEntity.ok()
                .body(RestResponse.ofFailure(TranslationKey.Common.Message.REQUEST_VALIDATION_FAILURE));
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

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResourceFoundException(NoResourceFoundException exception) {
        log.error("[NOT_FOUND] : {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Void> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        log.error("[METHOD_NOT_ALLOWED] : {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Void>> handleException(Exception exception) {
        log.error("[ERROR] : {}", exception.getMessage());

        return ResponseEntity.internalServerError()
                .body(RestResponse.ofFailure(TranslationKey.Common.Message.INTERNAL_SERVER_ERROR_FAILURE));
    }
}
