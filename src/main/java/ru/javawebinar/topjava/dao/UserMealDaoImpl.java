package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserMealDaoImpl implements UserMealDao{
    private Map<Integer, Meal> userMeals = new ConcurrentHashMap<>();
    private AtomicInteger atomicId = new AtomicInteger(0);

    {
                addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
                addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
                addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
                addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
                addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
                addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
                addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Meal addMeal(Meal meal) {
        if (meal.isNew()) {
            meal.setId(atomicId.incrementAndGet());
        }
        return userMeals.put(meal.getId(), meal);
    }

    @Override
    public void deleteMealById(int id) {
        userMeals.remove(id);
    }

    @Override
    public Meal getMealById(int id) {
        return userMeals.get(id);
    }

    @Override
    public Collection<Meal> getAllMeals() {
        return userMeals.values();
    }
}