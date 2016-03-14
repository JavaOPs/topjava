package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDate;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {
    UserMeal save(UserMeal userMeal, int userID);

    boolean delete(int id, int userId);

    UserMeal get(int id, int userID);

    Collection<UserMeal> getAll(int userID);

    Collection<UserMeal> getBetween(LocalDate startDate, LocalDate endDate, int UserID);
}
