package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static ru.javawebinar.topjava.util.UserMealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.UserMealsUtil.getFilteredWithExceeded;

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
    public boolean delete(int id) {
        LOG.info("remove" + id);
        return service.remove(id);
    }

    @Override
    public boolean delete(UserMeal userMeal) {
        return delete(userMeal.getId());
    }

    @Override
    public UserMeal save(UserMeal userMeal) {
        LOG.info("save" + userMeal.getId());
        return service.save(userMeal);
    }

    @Override
    public boolean update(UserMeal userMeal) {
        LOG.info("update" + userMeal.getId());
        return service.update(userMeal);
    }

    @Override
    public UserMeal get(int id) {
        LOG.info("get" + id);
        return service.get(id);
    }


    @Override
    public Collection<UserMealWithExceed> getAllMealByUser(User user) {
        return getAllMealByUser(user, DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public Collection<UserMealWithExceed> getAllMealByUserBetweenDateTime(User user, LocalDateTime startTime, LocalDateTime endTime) {
        return getAllMealByUserBetweenDateTime(user, startTime, endTime, DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public boolean delete(User user) {
        Collection<UserMeal> coll = service.getAllUserMeal().stream()
                .filter(userMeal -> Objects.equals(userMeal.getUsersID(), user.getId())).collect(toList());
        coll.forEach(this::delete);
        return coll.size() > 0;
    }

    @Override
    public Collection<UserMealWithExceed> getAllMealByUser(User user, int calories) {
        return UserMealsUtil.getWithExceeded(service.getAllUserMeal().stream()
                .filter(userMeal -> Objects.equals(userMeal.getUsersID(), user.getId())).collect(toList()), calories);
    }

    @Override
    public Collection<UserMealWithExceed> getAllMealByUserBetweenDateTime(User user,
                                                                          LocalDateTime startTime,
                                                                          LocalDateTime endTime, int calories) {
        return getFilteredWithExceeded(service.getAllUserMeal().stream()
                .filter(um -> Objects.equals(um.getUsersID(), user.getId())).collect(toList()), startTime, endTime, calories);
    }

}
