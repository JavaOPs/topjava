package ru.javawebinar.topjava.util;

import java.time.LocalTime;

public class TimeUtil {

    private TimeUtil() {}

    public static boolean isBeetwen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }
}