package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.FilterHolder;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class AuthorizedUser {
    private static FilterHolder filter = new FilterHolder(null, null, null, null);

    public static FilterHolder getFilter() {
        return filter;
    }

    public static void setFilter(FilterHolder filterSet) {
        filter = filterSet;
    }

    public static int id() {
        return 1;
    }

    public static int getCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}