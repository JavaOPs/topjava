package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface UserMealDao {
    Meal create(Meal meal);
    Meal update(Meal meal);
    void deleteById(int id);
    Meal getById(int id);
    Collection<Meal> getAll();
}