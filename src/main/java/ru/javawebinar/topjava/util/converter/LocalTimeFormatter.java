package ru.javawebinar.topjava.util.converter;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.TimeUtil;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * GKislin
 * 15.04.2015.
 */
public class LocalTimeFormatter implements Formatter<LocalTime> {

    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return TimeUtil.parseLocalTime(text, null);
    }

    @Override
    public String print(LocalTime lt, Locale locale) {
        return lt.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
