package ru.javawebinar.topjava.web;

import lombok.Getter;
import lombok.Setter;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

/**
 * @author Alexei Valchuk, 07.02.2023, email: a.valchukav@gmail.com
 */

public class SecurityUtil {

    @Getter
    @Setter
    private static int id = 1;

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}
