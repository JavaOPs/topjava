package ru.javawebinar.topjava.util.formatters;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return LocalDate.parse(text, DateTimeFormatter.ISO_DATE.withLocale(locale));
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object == null ? null : object.format(DateTimeFormatter.ISO_DATE.withLocale(locale));
    }
}
