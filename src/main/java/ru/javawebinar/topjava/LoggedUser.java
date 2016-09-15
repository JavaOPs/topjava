package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.UserMealsUtil;

import ru.javawebinar.topjava.model.BaseEntity;

/**
 * GKislin
 * 06.03.2015.
 */
public class LoggedUser {
    public static int id = BaseEntity.START_SEQ;

    public static int id() {
        return id;
    }

    public static void setId(int id) {
        LoggedUser.id = id;
    }

    public static int getCaloriesPerDay() {
        return UserMealsUtil.DEFAULT_CALORIES_PER_DAY;
    }
}
