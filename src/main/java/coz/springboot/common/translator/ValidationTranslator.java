package coz.springboot.common.translator;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import coz.springboot.common.constant.TranslationKey;
import coz.springboot.common.dto.RestResponse.Failure;

@Component
public class ValidationTranslator {

    public Failure translate(FieldError fieldError) {
        String constraintName = extractConstraintName(fieldError);
        return switch (constraintName) {
            case "NotNull", "NotBlank", "NotEmpty" -> translateRequired(fieldError);
            case "Size" -> translateSize(fieldError);
            case "Min", "Max" -> translateBound(fieldError, constraintName);
            default -> Failure.of(TranslationKey.Common.Message.REQUEST_VALIDATION_FAILURE);
        };
    }

    private String extractConstraintName(FieldError fieldError) {
        String[] codes = fieldError.getCodes();
        return (codes != null && codes.length > 0) ? codes[codes.length - 1] : "";
    }

    private Failure translateRequired(FieldError fieldError) {
        return Failure.of(TranslationKey.Common.Message.REQUIRED_VALIDATION_FAILURE, fieldError.getDefaultMessage());
    }

    private Failure translateSize(FieldError fieldError) {
        String fieldLabelKey = fieldError.getDefaultMessage();
        Object[] args = fieldError.getArguments();
        if (args == null || args.length < 3) {
            return Failure.of(TranslationKey.Common.Message.REQUEST_VALIDATION_FAILURE);
        }

        int max = (Integer) args[1];
        int min = (Integer) args[2];

        if (min > 0 && max < Integer.MAX_VALUE) {
            return Failure.of(
                    TranslationKey.Common.Message.RANGE_SIZE_VALIDATION_FAILURE,
                    fieldLabelKey,
                    String.valueOf(min),
                    String.valueOf(max));
        }
        if (min > 0) {
            return Failure.of(
                    TranslationKey.Common.Message.MIN_SIZE_VALIDATION_FAILURE, fieldLabelKey, String.valueOf(min));
        }
        if (max < Integer.MAX_VALUE) {
            return Failure.of(
                    TranslationKey.Common.Message.MAX_SIZE_VALIDATION_FAILURE, fieldLabelKey, String.valueOf(max));
        }

        return Failure.of(TranslationKey.Common.Message.REQUEST_VALIDATION_FAILURE);
    }

    private Failure translateBound(FieldError fieldError, String constraintName) {
        String fieldLabelKey = fieldError.getDefaultMessage();
        Object[] args = fieldError.getArguments();
        if (args == null || args.length < 2) {
            return Failure.of(TranslationKey.Common.Message.REQUEST_VALIDATION_FAILURE);
        }

        String messageKey = "Min".equals(constraintName)
                ? TranslationKey.Common.Message.MIN_SIZE_VALIDATION_FAILURE
                : TranslationKey.Common.Message.MAX_SIZE_VALIDATION_FAILURE;
        return Failure.of(messageKey, fieldLabelKey, String.valueOf(args[1]));
    }
}
