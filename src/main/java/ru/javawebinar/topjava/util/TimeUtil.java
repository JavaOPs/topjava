package ru.javawebinar.topjava.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;
import java.util.function.Function;

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


    public static <T extends Comparable<? super T>> boolean isBetween(T lt, T start, T end) {
        return start.compareTo(end) < 0 ? (lt.compareTo(start) >= 0 && lt.compareTo(end) <= 0) :
                isBetween(lt, end, start);
    }

    public static LocalDate parseLocalDate(String str, LocalDate def) {
        return StringUtils.isEmpty(str) ? def : LocalDate.parse(str);
    }

    public static LocalTime parseLocalTime(String str, LocalTime def) {
        return StringUtils.isEmpty(str) ? def : LocalTime.parse(str);
    }

    public static <T> T parse(String source, String pattern, T def, BiFunction<String, String, T> function) {
        return StringUtils.isEmpty(source) ? parse(def.toString(), pattern, def, function) : function.apply(source, pattern);
    }

    public static <T> T parse(String source, T def, Function<String, T> function) {
        return StringUtils.isEmpty(source) ? parse(def.toString(), def, function) : function.apply(source);
    }
}

