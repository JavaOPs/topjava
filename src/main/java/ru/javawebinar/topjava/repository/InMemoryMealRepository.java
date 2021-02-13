package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository{
    private Map<Integer, Meal> mealRepo = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new Meal(LocalDateTime.of(2021, Month.FEBRUARY,13, 9,0), "Zavtrak", 500));
        save(new Meal(LocalDateTime.of(2021, Month.FEBRUARY,13, 13,0), "Obed", 1500));
        save(new Meal(LocalDateTime.of(2021, Month.FEBRUARY,13, 19,0), "Uzhin", 500));
        save(new Meal(LocalDateTime.of(2021, Month.FEBRUARY,13, 9,0), "Zavtrak", 510));
        save(new Meal(LocalDateTime.of(2021, Month.FEBRUARY,13, 13,0), "Obed", 900));
        save(new Meal(LocalDateTime.of(2021, Month.FEBRUARY,13, 19,0), "Uzhin", 100));
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()){
            meal.setId(counter.incrementAndGet());
        }
        return mealRepo.put(meal.getId(),meal);
    }

    @Override
    public void delete(int id) {
        mealRepo.remove(id);
    }

    @Override
    public Meal get(int id) {
        return mealRepo.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return mealRepo.values();
    }
}
