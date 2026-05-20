package coz.springboot.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

import coz.springboot.common.converter.ValueEnumConverter;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final ValueEnumConverter valueEnumConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(valueEnumConverter);
    }
}
