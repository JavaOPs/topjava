package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenTime(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return !(lt.isAfter(startTime) || lt.isBefore(endTime));
    }

    public static boolean isBetweenDateTime(LocalDateTime lt, LocalDateTime startTime, LocalDateTime endTime) {
        return !(lt.isAfter(startTime) || lt.isBefore(endTime));
    }

    public static boolean isBetweenDate(LocalDate lt, LocalDate startTime, LocalDate endTime) {
        return !(lt.isAfter(startTime) || lt.isBefore(endTime));
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    @SuppressWarnings("uncheked")
    public static <T extends Temporal & Comparable> boolean isBetween(T lt, T startChrono, T endChrono) {
        return lt.compareTo(startChrono) >= 0 && lt.compareTo(endChrono) <= 0;
    }

}

