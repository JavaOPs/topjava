package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

/**
 * @author Alexei Valchuk, 15.02.2023, email: a.valchukav@gmail.com
 */

public class UserTestData extends AbstractTestData<User> {

    public static final int USER_ID = 100000;
    public static final int ADMIN_ID = 100001;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);

    public UserTestData(String... fieldsToIgnore) {
        super(fieldsToIgnore);
    }
}
