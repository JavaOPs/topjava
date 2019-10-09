package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Repo  implements DBManager {
    private static  List<Meal> mealToStorage = new ArrayList<>();
    static {
                mealToStorage.add(new Meal(1,LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealToStorage.add (new Meal(2,LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealToStorage.add (new Meal(3,LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealToStorage.add (new Meal(4,LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealToStorage.add (new Meal(5,LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealToStorage.add  (new Meal(6,LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    };


    @Override
    public  void create(Meal meal) {
        Repo.mealToStorage.add(meal);
    }

    @Override
    public void update(Meal meal) {
        Repo.mealToStorage.set(mealToStorage.indexOf(meal),meal);
    }

    @Override
    public void delete(Integer id) {
        Repo.mealToStorage.remove(mealToStorage.get(id));
    }

    public static List<Meal> getMealToStorage() {
        return mealToStorage;
    }
}
