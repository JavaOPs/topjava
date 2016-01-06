package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

/**
 * GKislin
 * 24.09.2015.
 */
public class UserTestData {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    public static final User USER = new User(1, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(2, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);
}
