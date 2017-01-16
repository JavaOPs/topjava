package ru.javawebinar.topjava.web.user;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;

/**
 * GKislin
 * 06.03.2015.
 */
public class ProfileRestController extends AbstractUserController {

    public User get() {
        return super.get(AuthorizedUser.id());
    }

    public void delete() {
        super.delete(AuthorizedUser.id());
    }

    public void update(User user) {
        super.update(user, AuthorizedUser.id());
    }
}