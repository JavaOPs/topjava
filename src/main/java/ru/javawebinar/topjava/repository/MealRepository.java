package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal);

    boolean delete(int id, int userID);

    Meal get(int id, int userID);

    Collection<Meal> getAll(int userID);

    Collection<Meal> getDataFiltered(LocalDate beginDate, LocalDate endDate, int userID);
}
