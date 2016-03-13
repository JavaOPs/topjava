package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.Collection;


/**
 * GKislin
 * 15.06.2015.
 */

public interface UserMealService {
    UserMeal save(UserMeal userMeal);

    boolean update(UserMeal userMeal);

    UserMeal get(int id);

    boolean remove(UserMeal userMeal);

    boolean remove(int id);

    Collection<UserMeal> getAllUserMeal();
}