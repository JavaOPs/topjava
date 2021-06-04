package ru.javawebinar.topjava.util.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToDateConverter implements Converter<String, LocalDate> {
    private final DateTimeFormatter df;

    public StringToDateConverter(String pattern) {
        df = DateTimeFormatter.ofPattern(pattern);
    }
    @Override
    public LocalDate convert(String source) {
        return source.isEmpty() ? null : LocalDate.parse(source, df);
    }
}
