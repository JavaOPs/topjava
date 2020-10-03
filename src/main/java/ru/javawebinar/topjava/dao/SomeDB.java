package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.util.MealsUtil.*;

public class SomeDB {
    private final List<Meal> mealsData = new CopyOnWriteArrayList();
    private final AtomicInteger id = new AtomicInteger();
    private static SomeDB someDB;

    private SomeDB() {
        addSomeDataToDb();
    }

    public static SomeDB instanceSomeDB() {
        if(someDB == null) {
            synchronized(SomeDB.class) {
                if(someDB == null) {
                    someDB = new SomeDB();
                }
            }
        }
        return someDB;
    }

    private void addSomeDataToDb() {
        mealsData.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, id.incrementAndGet()));
        mealsData.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, id.incrementAndGet()));
        mealsData.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, id.incrementAndGet()));
        mealsData.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, id.incrementAndGet()));
        mealsData.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, id.incrementAndGet()));
        mealsData.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500, id.incrementAndGet()));
        mealsData.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410, id.incrementAndGet()));
    }

    public List<Meal> getAllDataFromDb(){
        return mealsData;
    }
}
