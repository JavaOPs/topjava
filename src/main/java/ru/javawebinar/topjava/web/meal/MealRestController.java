package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll meals");
        return service.getAll(authUserId());
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("filter all meals by(start/end) date:{}/{} and time:{}/{}", startDate, endDate, startTime, endTime);
        return service.getFiltered(authUserId(), LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime));
    }

    public Meal get(int mealId) {
        log.info("get meal with id={}", mealId);
        return service.get(authUserId(), mealId);
    }

    public Meal create(Meal meal) {
        log.info("create meal {}", meal);
        checkNew(meal);
        return service.create(authUserId(), meal);
    }

    public void delete(int mealId) {
        log.info("delete meal with id={}", mealId);
        service.delete(authUserId(), mealId);
    }

    public void update(Meal meal, int mealId) {
        log.info("update meal {} with id={}", meal, mealId);
        assureIdConsistent(meal, mealId);
        service.update(authUserId(), meal);
    }

    public void save(Meal meal) {
        log.info("update meal {}", meal);
        if (meal.isNew()) {
            create(meal);
        } else {
            update(meal, meal.getId());
        }
    }
}