package ru.javawebinar.topjava.util.formatters;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return LocalTime.parse(text, DateTimeFormatter.ISO_DATE.withLocale(locale));
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return object == null ? null : object.format(DateTimeFormatter.ISO_DATE.withLocale(locale));
    }
}
