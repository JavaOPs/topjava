package ru.javawebinar.topjava.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

/**
 * @author Alexei Valchuk, 13.02.2023, email: a.valchukav@gmail.com
 */

@UtilityClass
public class Util {

    public static <T extends Comparable<? super T>> boolean isBetweenInclusive(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) <= 0);
    }
}
