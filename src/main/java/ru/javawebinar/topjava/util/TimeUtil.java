package ru.javawebinar.topjava.util;

import java.time.LocalTime;

/**
 * Created by xcoder on 09/04/16.
 */
public class TimeUtil {
    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
               return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
           }
}
