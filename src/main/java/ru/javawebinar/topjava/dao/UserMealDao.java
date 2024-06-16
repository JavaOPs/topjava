package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface UserMealDao {
    Meal addMeal(Meal meal);
    void deleteMealById(int id);
    Meal getMealById(int id);
    Collection<Meal> getAllMeals();
}