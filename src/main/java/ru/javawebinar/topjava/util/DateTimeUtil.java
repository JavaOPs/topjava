package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpenTime(LocalTime lt, LocalTime start, LocalTime end) {
        return lt.compareTo(start) >= 0 && lt.compareTo(end) < 0;
    }

    public static boolean isBetweenDate(LocalDate lt, LocalDate start, LocalDate end) {
        return lt.compareTo(start) >= 0 && lt.compareTo(end) <= 0;
    }

    public static <T> T replaceIfNull(T aim, T replacement) {
        return (aim == null) ? replacement : aim;
    }

    public static LocalDate parseToDateOrReturnNull(String aim) {
        return (aim.isEmpty()) ? null : LocalDate.parse(aim);
    }

    public static LocalTime parseToTimeOrReturnNull(String aim) {
        return (aim.isEmpty()) ? null : LocalTime.parse(aim);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

