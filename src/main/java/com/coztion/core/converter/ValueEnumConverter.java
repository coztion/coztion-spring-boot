package com.coztion.core.converter;

import java.util.Collections;
import java.util.Set;

import com.coztion.common.enums.ValueEnum;

import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.stereotype.Component;

@Component
public class ValueEnumConverter implements ConditionalGenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Enum.class));
    }

    @Override
    public boolean matches(@NonNull TypeDescriptor sourceType, TypeDescriptor targetType) {
        return ValueEnum.class.isAssignableFrom(targetType.getType());
    }

    @Override
    public Object convert(Object source, @NonNull TypeDescriptor sourceType, TypeDescriptor targetType) {
        String value = (String) source;
        ValueEnum[] enums = (ValueEnum[]) targetType.getType().getEnumConstants();
        for (ValueEnum e : enums) {
            if (e.getValue().equalsIgnoreCase(value)) return e;
        }
        return null;
    }
}
