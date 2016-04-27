package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.util.UserUtil;

/**
 * GKislin
 * 06.03.2015.
 */
public class LoggedUser {
    private static int id = BaseEntity.START_SEQ;

    private LoggedUser() {
    }

    public static int id() {
        return id;
    }

    public static void setId(int id) {
        LoggedUser.id = id;
    }

    public static int getCaloriesPerDay() {
        return UserUtil.DEFAULT_CALORIES_PER_DAY;
    }
}
