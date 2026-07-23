package coz.springboot.common.translator;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import lombok.RequiredArgsConstructor;

import coz.springboot.common.constant.Message;

@Component
@RequiredArgsConstructor
public class ValidationTranslator {

    private final MessageTranslator messageTranslator;

    public String translate(FieldError fieldError) {
        String constraintName = extractConstraintName(fieldError);
        return switch (constraintName) {
            case "NotNull", "NotBlank", "NotEmpty" -> translateRequired(fieldError);
            case "Size" -> translateSize(fieldError);
            case "Min", "Max" -> translateBound(fieldError, constraintName);
            default -> messageTranslator.translate(Message.Common.Failure.REQUEST_VALIDATION_FAILURE);
        };
    }

    private String extractConstraintName(FieldError fieldError) {
        String[] codes = fieldError.getCodes();
        return (codes != null && codes.length > 0) ? codes[codes.length - 1] : "";
    }

    private String translateRequired(FieldError fieldError) {
        return messageTranslator.translate(
                Message.Common.Failure.REQUIRED_VALIDATION_FAILURE, fieldError.getDefaultMessage());
    }

    private String translateSize(FieldError fieldError) {
        String fieldLabel = messageTranslator.translate(fieldError.getDefaultMessage());
        Object[] args = fieldError.getArguments();
        if (args == null || args.length < 3) {
            return messageTranslator.translate(Message.Common.Failure.REQUEST_VALIDATION_FAILURE);
        }

        int max = (Integer) args[1];
        int min = (Integer) args[2];

        if (min > 0 && max < Integer.MAX_VALUE) {
            return messageTranslator.translateWithArgs(
                    Message.Common.Failure.RANGE_SIZE_VALIDATION_FAILURE, fieldLabel, min, max);
        }
        if (min > 0) {
            return messageTranslator.translateWithArgs(
                    Message.Common.Failure.MIN_SIZE_VALIDATION_FAILURE, fieldLabel, min);
        }
        if (max < Integer.MAX_VALUE) {
            return messageTranslator.translateWithArgs(
                    Message.Common.Failure.MAX_SIZE_VALIDATION_FAILURE, fieldLabel, max);
        }

        return messageTranslator.translate(Message.Common.Failure.REQUEST_VALIDATION_FAILURE);
    }

    private String translateBound(FieldError fieldError, String constraintName) {
        String fieldLabel = messageTranslator.translate(fieldError.getDefaultMessage());
        Object[] args = fieldError.getArguments();
        if (args == null || args.length < 2) {
            return messageTranslator.translate(Message.Common.Failure.REQUEST_VALIDATION_FAILURE);
        }

        String messageCode = "Min".equals(constraintName)
                ? Message.Common.Failure.MIN_SIZE_VALIDATION_FAILURE
                : Message.Common.Failure.MAX_SIZE_VALIDATION_FAILURE;
        return messageTranslator.translateWithArgs(messageCode, fieldLabel, args[1]);
    }
}
