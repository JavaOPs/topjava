package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Aspire on 07.12.2016.
 */
interface MealDAO {
    public void addMeal(Meal meal);

    public void updateMeal(Meal meal);

    public Meal getMealById(int id);

    public void removeMeal(int id);
}
