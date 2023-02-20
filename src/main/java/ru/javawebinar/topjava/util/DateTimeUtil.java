package ru.javawebinar.topjava.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class DateTimeUtil {

    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return Util.isBetweenInclusive(lt, startTime, endTime);
    }

    public static String toString(LocalDateTime dateTime) {
        return dateTime == null? "" : dateTime.format(DATE_TIME_FORMATTER);
    }

    public static @Nullable LocalDate parseLocalDate(@Nullable String str) {
        return !StringUtils.hasText(str) ? null : LocalDate.parse(str);
    }

    public static @Nullable LocalTime parseLocalTime(@Nullable String str) {
        return !StringUtils.hasText(str) ? null : LocalTime.parse(str);
    }

    public static LocalDateTime getStartInclusive(LocalDate localDate) {
        return startOfDay(localDate != null ? localDate : MIN_DATE);
    }

    public static LocalDateTime getEndExclusive(LocalDate localDate) {
        return startOfDay(localDate != null ? localDate.plus(1, ChronoUnit.DAYS) : MAX_DATE);
    }

    private static LocalDateTime startOfDay(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }
}
