package coz.springboot.helper;

import java.util.Arrays;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageTranslator {

    private final MessageSource messageSource;

    public String translate(String messageCode) {
        return messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale());
    }

    public String translate(String messageCode, String... labelCodes) {
        String[] labels = Arrays.stream(labelCodes).map(this::translate).toArray(String[]::new);

        return messageSource.getMessage(messageCode, labels, LocaleContextHolder.getLocale());
    }
}
