package ru.javawebinar.topjava;


import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static ru.javawebinar.topjava.util.UserMealsUtil.DEFAULT_CALORIES_PER_DAY;

/**
 * GKislin
 * 06.03.2015.
 */
public enum LoggedUser {
    LOGGED_USER;
    public static int id = 1;
    private final Map<String, Integer> userIDs = new ConcurrentHashMap<>();
    protected Set<Role> roles = Collections.singleton(Role.ROLE_USER);
    protected boolean enabled = true;

    public static int getCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

    public static int id() {
        return id;
    }

    public static void setId(int id) {
        LoggedUser.id = id;
    }

    public Integer id(String str) {
        return ExceptionUtil.check(userIDs.get(str), "Not Logged user");
    }

    public Integer saveUserLogin(String str, int id) {
        return userIDs.put(str, id);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
