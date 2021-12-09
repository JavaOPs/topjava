package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, @Nullable LocalTime startTime, @Nullable LocalTime endTime) {
        return (startTime == null || lt.compareTo(startTime) >= 0) && (endTime == null || lt.compareTo(endTime) < 0);
    }

    public static boolean isDateBetweenHalfOpen(LocalDateTime mealDate, @Nullable LocalDateTime startDate, @Nullable LocalDateTime endDate) {
        return (startDate == null || mealDate.compareTo(startDate) >= 0) && (endDate == null || mealDate.compareTo(endDate) < 0);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

