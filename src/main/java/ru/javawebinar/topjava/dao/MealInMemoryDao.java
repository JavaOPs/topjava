package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealInMemoryDao implements MealDao {
    private static int id = 0;
    private static final Map<Integer, Meal> meals = new HashMap<>();

    static {
        meals.put(++id, new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.put(++id, new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.put(++id, new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.put(++id, new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.put(++id, new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.put(++id, new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.put(++id, new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Meal add(LocalDateTime dateTime, String description, int calories) {
        synchronized (MealInMemoryDao.class) {
            Meal meal = new Meal(++id, dateTime, description, calories);
            meals.put(meal.getId(), meal);
            return meal;
        }
    }

    @Override
    public List<Meal> getAll() {
        synchronized (MealInMemoryDao.class) {
            return new ArrayList<>(meals.values());
        }
    }

    @Override
    public Meal getById(int id) {
        synchronized (MealInMemoryDao.class) {
            return meals.get(id);
        }
    }

    @Override
    public Meal update(int id, LocalDateTime dateTime, String description, int calories) {
        synchronized (MealInMemoryDao.class) {
            Meal meal = new Meal(id, dateTime, description, calories);
            meals.put(id, meal);
            return meal;
        }
    }

    @Override
    public void delete(int id) {
        synchronized (MealInMemoryDao.class) {
            meals.remove(id);
        }
    }
}
