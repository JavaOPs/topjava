package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {

    public static final List<User> users = Arrays.asList(
            new User(null, "name_1", "email_1", "password_1", Role.USER),
            new User(null, "name_2", "email_2", "password_2", Role.USER),
            new User(null, "same_name", "email_3", "password_3", Role.USER),
            new User(null, "same_name", "email_4", "password_4", Role.USER)
    );
}
