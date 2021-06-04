package ru.javawebinar.topjava.util.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class StringToTimeConverter implements Converter<String, LocalTime> {
    private final DateTimeFormatter df;

    public StringToTimeConverter(String pattern) {
        df = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public LocalTime convert(String source) {
        return source.isEmpty() ? null : LocalTime.parse(source, df);
    }
}
