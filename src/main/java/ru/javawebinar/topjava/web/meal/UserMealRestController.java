package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static java.time.LocalDate.MAX;
import static java.time.LocalDate.MIN;
import static java.util.stream.Collectors.toList;
import static ru.javawebinar.topjava.LoggedUser.getCaloriesPerDay;
import static ru.javawebinar.topjava.LoggedUser.id;
import static ru.javawebinar.topjava.util.TimeUtil.isBetween;
import static ru.javawebinar.topjava.util.UserMealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.UserMealsUtil.getWithExceeded;

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
        LOG.info("get meal {} for User {}", id, id());
        return service.get(id, id());
    }

    @Override
    public void delete(int id) {
        LOG.info("delete meal {} for User {}", id, id());
        service.remove(id, id());
    }


    @Override
    public void deleteAll() {
        LOG.info("delete all meal for User {}", id());
        service.deleteAllMeal(id());
    }

    @Override
    public void update(UserMeal meal) {
        LOG.info("update meal {} for User {}", meal, id());
        service.update(meal, id());
    }

    @Override
    public UserMeal create(UserMeal meal) {
        LOG.info("get meal {} for User {}", meal, id());
        return service.save(meal, id());
    }

    @Override
    public Collection<UserMeal> getBetween(LocalDate start, LocalDate endTime) {
        LOG.info("get all meal for User {} between {}, {}", id(), start, endTime);
        return service.getBetweenUserMeal(id(), start, endTime);
    }

    @Override
    public Collection<UserMealWithExceed> getAllExceedMeal() {
        return getBetweenExceedMeal(MIN, MAX, getCaloriesPerDay());
    }

    @Override
    public Collection<UserMealWithExceed> getBetweenExceedMeal(LocalDate start, LocalDate end) {
        return getBetweenExceedMeal(start, end, getCaloriesPerDay());
    }

    @Override
    public Collection<UserMealWithExceed> getAllExceedMeal(int calories) {
        return getBetweenExceedMeal(MIN, MAX, calories);
    }

    @Override
    public Collection<UserMealWithExceed> getBetweenExceedMeal(LocalDate start, LocalDate end, int calories) {
        LOG.info("get meal witch exceed for User {} witch calories {}", id());
        return getWithExceeded(service.getAllUserMeal(id()), calories).stream()
                .filter(userMealWithExceed -> isBetween(userMealWithExceed.getDateTime().toLocalDate(), start, end))
                .collect(toList());
    }

    @Override
    public Collection<UserMealWithExceed> getBetweenExceedMealWitchTime(LocalDate startD, LocalDate endD,
                                                                        LocalTime start, LocalTime end, int calories) {
        return getBetweenExceedMeal(startD, endD, calories).stream()
                .filter(userMealWithExceed -> isBetween(userMealWithExceed.getDateTime().toLocalTime(), start, end))
                .collect(toList());
    }

    @Override
    public Collection<UserMealWithExceed> getBetweenExceedMealWitchTime(LocalDate startD, LocalDate endD,
                                                                        LocalTime start, LocalTime end) {
        return getBetweenExceedMealWitchTime(startD, endD, start, end, DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public Collection<UserMeal> getAll() {
        LOG.info("get all meal for User {}", id());
        return service.getAllUserMeal(id());
    }
}
