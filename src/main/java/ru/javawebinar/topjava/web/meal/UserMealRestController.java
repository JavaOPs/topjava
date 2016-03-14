package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;

import java.time.LocalDate;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController implements MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(User.class);
    @Autowired
    private UserMealService service;

    @Override
    public UserMeal get(int id) {
        LOG.info("get meal {} for User {}", id, LoggedUser.id());
        return service.get(id, LoggedUser.id());
    }

    @Override
    public void delete(int id) {
        LOG.info("delete meal {} for User {}", id, LoggedUser.id());
        service.remove(id, LoggedUser.id());
    }


    @Override
    public void deleteAll() {
        LOG.info("delete all meal for User {}", LoggedUser.id());
        service.deleteAllMeal(LoggedUser.id());
    }

    @Override
    public void update(UserMeal meal) {
        LOG.info("update meal {} for User {}", meal, LoggedUser.id());
        service.update(meal, LoggedUser.id());
    }

    @Override
    public UserMeal create(UserMeal meal) {
        LOG.info("get meal {} for User {}", meal, LoggedUser.id());
        return service.save(meal, LoggedUser.id());
    }

    @Override
    public Collection<UserMeal> getBetween(LocalDate start, LocalDate endTime) {
        LOG.info("get all meal for User {} between {}, {}", LoggedUser.id(), start, endTime);
        return service.getBetweenUserMeal(LoggedUser.id(), start, endTime);
    }

    @Override
    public Collection<UserMeal> getAll() {
        LOG.info("get all meal for User {}", LoggedUser.id());
        return service.getAllUserMeal(LoggedUser.id());
    }
}
