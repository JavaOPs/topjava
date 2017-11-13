package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealService {
    Meal save(Meal meal);

    void delete(int id, int userID) throws NotFoundException;

    Meal get(int id, int userID) throws NotFoundException;

    Collection<MealWithExceed> getAll(int userID, int caloriesPerDay);

    Collection<MealWithExceed> getTimeDataFiltered(LocalDate beginDate, LocalDate endDate,
                                                   LocalTime beginTime, LocalTime endTime,
                                                   int userID, int caloriesPerDay);
}