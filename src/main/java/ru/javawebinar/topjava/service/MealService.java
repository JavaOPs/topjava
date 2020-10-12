package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class MealService {
    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(int userId, Meal meal) {
        return repository.save(userId, meal);
    }

    public void update(int userId, Meal meal) {
        checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }

    public void delete(int userId, int mealId) {
        checkNotFoundWithId(repository.delete(userId, mealId), mealId);
    }

    public Meal get(int userId, int mealId) {
        return checkNotFoundWithId(repository.get(userId, mealId), mealId);
    }

    public List<MealTo> getAll(int userId) {
        return MealsUtil.getTos(getAllNotModify(userId),
                MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getFiltered(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return MealsUtil.getFilteredByDateTimeTos(getAllNotModify(userId),
                MealsUtil.DEFAULT_CALORIES_PER_DAY,
                DateTimeUtil.replaceIfNull(startDateTime, LocalDate.MIN, LocalTime.MIN),
                DateTimeUtil.replaceIfNull(endDateTime, LocalDate.MAX, LocalTime.MAX));
    }

    private List<Meal> getAllNotModify(int userId) {
        return repository.getAll(userId);
    }
}