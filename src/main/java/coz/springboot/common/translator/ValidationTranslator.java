package coz.springboot.common.translator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
            default -> messageTranslator.translate(Message.Exception.BAD_REQUEST);
        };
    }

    private String extractConstraintName(FieldError fieldError) {
        String[] codes = fieldError.getCodes();
        return (codes != null && codes.length > 0) ? codes[codes.length - 1] : "";
    }

    private String translateRequired(FieldError fieldError) {
        if (!StringUtils.hasText(fieldError.getDefaultMessage())) {
            return messageTranslator.translate(Message.Validation.REQUIRED_MISSING);
        }
        return messageTranslator.translate(Message.Validation.FIELD_IS_REQUIRED, fieldError.getDefaultMessage());
    }

    private String translateSize(FieldError fieldError) {
        String fieldLabel = messageTranslator.translate(fieldError.getDefaultMessage());
        Object[] args = fieldError.getArguments();
        if (args == null || args.length < 3) {
            return messageTranslator.translateWithArgs(Message.Validation.INVALID_SIZE, fieldLabel);
        }

        int max = (Integer) args[1];
        int min = (Integer) args[2];

        if (min > 0 && max < Integer.MAX_VALUE) {
            return messageTranslator.translateWithArgs(Message.Validation.RANGE_SIZE_INVALID, fieldLabel, min, max);
        }
        if (min > 0) {
            return messageTranslator.translateWithArgs(Message.Validation.MIN_SIZE_INVALID, fieldLabel, min);
        }
        if (max < Integer.MAX_VALUE) {
            return messageTranslator.translateWithArgs(Message.Validation.MAX_SIZE_INVALID, fieldLabel, max);
        }
        return messageTranslator.translateWithArgs(Message.Validation.INVALID_SIZE, fieldLabel);
    }

    private String translateBound(FieldError fieldError, String constraintName) {
        String fieldLabel = messageTranslator.translate(fieldError.getDefaultMessage());
        Object[] args = fieldError.getArguments();
        if (args == null || args.length < 2) {
            return messageTranslator.translate(Message.Exception.BAD_REQUEST);
        }

        String messageCode = "Min".equals(constraintName)
                ? Message.Validation.MIN_SIZE_INVALID
                : Message.Validation.MAX_SIZE_INVALID;
        return messageTranslator.translateWithArgs(messageCode, fieldLabel, args[1]);
    }
}
