package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    public static int authUserId = 1;

    public static int authUserId() {
        return authUserId;
    }

    public static void setAuthUserId(int userId) { SecurityUtil.authUserId = userId; }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}