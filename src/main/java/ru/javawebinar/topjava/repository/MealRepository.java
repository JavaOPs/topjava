package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

/**
 * @author Alexei Valchuk, 06.02.2023, email: a.valchukav@gmail.com
 */

public interface MealRepository {

    Meal save (Meal meal);

    void delete (int id);

    Meal get (int id);

    Collection<Meal> getAll();
}
