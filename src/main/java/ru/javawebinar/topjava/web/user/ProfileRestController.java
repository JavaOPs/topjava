package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

/**
 * @author Alexei Valchuk, 07.02.2023, email: a.valchukav@gmail.com
 */

@Controller
public class ProfileRestController extends AbstractUserController{

    @Autowired
    public ProfileRestController(UserService service) {
        super(service);
    }

    public User get() {
        return super.get(authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }

    public void update(User user) {
        super.update(user, authUserId());
    }
}
