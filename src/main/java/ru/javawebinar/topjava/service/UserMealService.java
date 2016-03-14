package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDate;
import java.util.Collection;


/**
 * GKislin
 * 15.06.2015.
 */

public interface UserMealService {
    UserMeal save(UserMeal userMeal, int userID);

    boolean update(UserMeal userMeal, int userID);

    UserMeal get(int id, int userID);

    boolean remove(int id, int userID);

    Collection<UserMeal> getAllUserMeal(int userID);

    Collection<UserMeal> getBetweenUserMeal(int userID, LocalDate start, LocalDate end);

    boolean deleteAllMeal(int userID);
}