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

    public static LocalDateTime replaceIfNull(LocalDateTime aim, LocalDate dateReplacement, LocalTime timeReplacement) {
        LocalDate date = aim.toLocalDate();
        LocalTime time = aim.toLocalTime();
        if (date == null || time == null) {
            aim = LocalDateTime.of(
                    (date == null) ? dateReplacement : date,
                    (time == null) ? timeReplacement : time
            );
        }
        return aim;
    }

    public static LocalDate parseOrReplaceIfNullWithDate(String aim, LocalDate dateReplacement) {
        return (aim == null)? dateReplacement : LocalDate.parse(aim);
    }

    public static LocalTime parseOrReplaceIfNullWithTime(String aim, LocalTime dateReplacement) {
        return (aim == null)? dateReplacement : LocalTime.parse(aim);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

