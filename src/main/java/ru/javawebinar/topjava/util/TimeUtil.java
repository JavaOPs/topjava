package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return Util.isBetweenInclusive(lt, startTime, endTime);
    }

    public static String toString(LocalDateTime dateTime) {
        return dateTime == null? "" : dateTime.format(DATE_TIME_FORMATTER);
    }
}
