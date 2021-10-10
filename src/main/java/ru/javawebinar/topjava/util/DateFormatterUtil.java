package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateFormatterUtil {

        private DateFormatterUtil() {}

        public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
            return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        }
}
