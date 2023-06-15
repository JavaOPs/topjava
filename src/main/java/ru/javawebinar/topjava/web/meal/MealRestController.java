package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.*;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService mealService;

    public MealTo save(Meal meal) {
        int userId = authUserId();
        log.info("user with id={} saves a mealTo with id={}", userId, meal.getId());
        return creatTo(userId, mealService.create(meal, userId));
    }

    public void delete(int id) {
        int userId = authUserId();
        log.info("user with id={} deletes a mealTo with id={}", userId, id);
        mealService.delete(id, userId);
    }

    public MealTo get(int id) {
        int userId = authUserId();
        log.info("user with id={} gets a mealTo with id={}", userId, id);
        return creatTo(userId, mealService.get(id, userId));
    }

    public List<MealTo> getAll() {
        int userId = authUserId();
        log.info("user with id={} gets all mealTos", userId);
        int caloriesPerDay = authUserCaloriesPerDay();
        return getTos(mealService.getAll(userId), caloriesPerDay);
    }

    public List<MealTo> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        int userId = authUserId();
        log.info("user with id={} gets List<MealTo> between half and open", userId);
        int caloriesPerDay = authUserCaloriesPerDay();
        List<Meal> meals = mealService.getBetweenOpen(startDate, endDate, userId);
        return getFilteredTos(
                meals,
                caloriesPerDay,
                startTime == null ? LocalTime.MIN : startTime,
                endTime == null ? LocalTime.MAX : endTime);
    }

    private MealTo creatTo(int userId, Meal meal) {
        boolean isExceeded = authUserCaloriesPerDay() > meal.getCalories();
        return createTo(meal, isExceeded);
    }
}