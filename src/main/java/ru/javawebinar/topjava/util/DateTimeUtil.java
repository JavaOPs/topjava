package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Temporal & Comparable> boolean isBetweenHalfOpen(T lt, T start, T end) {
        return lt.compareTo(start) >= 0 && lt.compareTo(end) < 0;
    }

    public static LocalDateTime replaceIfNull(LocalDateTime aim, LocalDateTime replacement) {
        LocalDate date = aim.toLocalDate();
        LocalTime time = aim.toLocalTime();
        if (date == null || time == null) {
            aim = LocalDateTime.of(
                    (date == null) ? replacement.toLocalDate() : date,
                    (time == null) ? replacement.toLocalTime() : time
            );
        }
        return aim;
    }

    public static LocalDate parseToDateOrReplaceIfEmpty(String aim, LocalDate dateReplacement) {
        return (aim.isEmpty()) ? dateReplacement : LocalDate.parse(aim);
    }

    public static LocalTime parseToTimeOrReplaceIfEmpty(String aim, LocalTime timeReplacement) {
        return (aim.isEmpty()) ? timeReplacement : LocalTime.parse(aim);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

