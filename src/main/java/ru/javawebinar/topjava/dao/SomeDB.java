package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class SomeDB {
    private final List<Meal> mealsData = new CopyOnWriteArrayList();
    private final AtomicLong id = new AtomicLong();
    private static SomeDB someDB;

    private SomeDB() {
        addSomeDataToDb();
    }

    public static SomeDB getConnectToDB() {
        if (someDB == null) {
            synchronized (SomeDB.class) {
                if (someDB == null) {
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

    public List<Meal> getAllDataFromDb() {
        return mealsData;
    }

    public Meal addMealToDb(Meal newMeal) {
        newMeal.setId(id.incrementAndGet());
        mealsData.add(newMeal);
        return newMeal;
    }

    public Meal updateMealInDb(Meal correctMeal) {
        deleteMealFromDb(getMealById(correctMeal.getId()));
        correctMeal.setId(id.incrementAndGet());
        mealsData.add(correctMeal);
        return correctMeal;
    }

    public void deleteMealFromDbById(Long deadMealsId) {
        Meal meal = getMealById(deadMealsId);
        if(meal != null) mealsData.remove(getMealById(deadMealsId));
    }

    public void deleteMealFromDb(Meal entity) {
        mealsData.remove(entity);
    }

    public Meal getMealById(Long deadMealsId) {
        return mealsData.stream()
                .filter(dbMeal -> dbMeal.getId() == deadMealsId)
                .findFirst()
                .orElse(null);
    }
}
