package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface InMemoryRepository {
    void add(Meal meal);

    void update(Meal meal);

    void delete(int id);

    Collection<Meal> getAll();

    Meal getById(int id);
}
