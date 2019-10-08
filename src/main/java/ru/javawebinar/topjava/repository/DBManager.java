package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

public interface DBManager {
    void create(Meal meal);

    void update(Meal meal);

    void delete(Meal meal);


}
