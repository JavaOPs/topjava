package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MealService {
    Meal save(Meal meal);

    void delete(int id, int userID) throws NotFoundException;

    Meal get(int id, int userID) throws NotFoundException;

    Collection<MealWithExceed> getAll(int userID);

    Collection<MealWithExceed> getTimeDataFiltered(LocalDateTime beginDate, LocalDateTime endDate, int userID);
}