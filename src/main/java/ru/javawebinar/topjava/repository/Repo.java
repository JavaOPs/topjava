package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class Repo  implements DBManager {
    private static final List<Meal> mealToStorage = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );


    @Override
    public  void create(Meal meal) {
        mealToStorage.add(meal);
    }

    @Override
    public void update(Meal meal) {
        mealToStorage.set(mealToStorage.indexOf(meal),meal);
    }

    @Override
    public void delete(Meal meal) {
        mealToStorage.remove(meal);
    }

    public static List<Meal> getMealToStorage() {
        return mealToStorage;
    }
}
