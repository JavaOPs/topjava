package ru.javawebinar.topjava.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public MealService(MealRepository repository) { this.repository = repository; }

    public void delete(int id, int userId) { checkNotFoundWithId(repository.delete(id, userId), id); }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public Collection<MealTo> getBetweenHalfOpen(int userId, int caloriesPerDay, LocalTime startTime, LocalTime endTime, @Nullable LocalDateTime startDate, @Nullable LocalDateTime endDate) {
        return repository.getBetweenHalfOpen(userId, caloriesPerDay, startTime, endTime, startDate, endDate);
    }
}