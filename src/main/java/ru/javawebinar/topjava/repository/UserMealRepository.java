package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {
    // null if updated meal do not belong to userId
    UserMeal save(UserMeal userMeal, int userId);

    // false if meal do not belong to userId
    boolean delete(int id, int userId);

    // null if meal do not belong to userId
    UserMeal get(int id, int userId);

    // ORDERED DATE, TIME
    Collection<UserMeal> getAll(int userId);

    // ORDERED DATE, TIME
    Collection<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);

    default UserMeal getWithUser(Integer id, Integer userId) {
        throw new UnsupportedOperationException();
    }
}
