package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UserUtils {
    public static List<User> USER = Arrays.asList(
            new User(null, "Alexey", "alex@ukr.net", "123", Role.ROLE_USER),
            new User (null, "Marina", "mar12@gmail.com", "pom33", Role.ROLE_USER),
            new User(null, "Anton", "baracuda@mail.ru", "yahoo", Role.ROLE_ADMIN)
    );
}
