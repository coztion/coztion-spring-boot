package com.coztion.common.translator;

import java.util.Arrays;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Deprecated(since = "2026.07.26 - MessageSource replaced with Translation domain")
@Component
@RequiredArgsConstructor
public class MessageTranslator {

    private static final String UNRESOLVED_MESSAGE_CODE = "__UNRESOLVED_MESSAGE_CODE__";

    private final MessageSource messageSource;

    public boolean hasExistingMessageCode(String messageCode) {
        String resolved =
                messageSource.getMessage(messageCode, null, UNRESOLVED_MESSAGE_CODE, LocaleContextHolder.getLocale());
        return !UNRESOLVED_MESSAGE_CODE.equals(resolved);
    }

    public String translate(String messageCode) {
        return messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale());
    }

    public String translate(String messageCode, String... labelCodes) {
        String[] labels = Arrays.stream(labelCodes).map(this::translate).toArray(String[]::new);

        return messageSource.getMessage(messageCode, labels, LocaleContextHolder.getLocale());
    }

    public String translateWithArgs(String messageCode, Object... args) {
        return messageSource.getMessage(messageCode, args, LocaleContextHolder.getLocale());
    }
}
