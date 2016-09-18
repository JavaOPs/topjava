package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.MealsUtil;

/**
 * GKislin
 * 06.03.2015.
 */
public class AuthorizedUser {

    public static int id() {
        return 1;
    }

    public static int getCaloriesPerDay() {
        return MealsUtil.DEFAULT_CALORIES_PER_DAY;
    }
}
