package com.coztion.core.converter;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import jakarta.annotation.PostConstruct;

import com.coztion.common.enums.ValueEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.stereotype.Component;

@Component
public class ValueEnumConverter extends SimpleModule implements ConditionalGenericConverter {

    @PostConstruct
    public void registerValueEnumDeserializerModifier() {
        setDeserializerModifier(new BeanDeserializerModifier() {
            @Override
            public JsonDeserializer<?> modifyEnumDeserializer(
                    DeserializationConfig config,
                    JavaType type,
                    BeanDescription beanDesc,
                    JsonDeserializer<?> deserializer) {
                if (!ValueEnum.class.isAssignableFrom(type.getRawClass())) {
                    return deserializer;
                }
                return new JsonDeserializer<Object>() {
                    @Override
                    public Object deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
                        String value = parser.getValueAsString();
                        for (Object constant : type.getRawClass().getEnumConstants()) {
                            ValueEnum valueEnum = (ValueEnum) constant;
                            if (valueEnum.getValue().equalsIgnoreCase(value)) {
                                return valueEnum;
                            }
                        }
                        return null;
                    }
                };
            }
        });
    }

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
