package ru.javawebinar.topjava.util;

import java.time.LocalTime;

public class TimeUtil {
    public static final LocalTime MIN_TIME = LocalTime.MIN;
    public static final LocalTime MAX_TIME = LocalTime.MAX;
    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }
}
