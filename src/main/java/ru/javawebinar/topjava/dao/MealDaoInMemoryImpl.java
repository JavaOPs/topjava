package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealDaoInMemoryImpl implements DaoInterface<Meal, Long> {
    private final Map<Long, Meal> mealsData = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong();

    public MealDaoInMemoryImpl() {
        addSomeDataToDb();
    }

    @Override
    public Meal save(Meal entity) {
        entity.setId(id.incrementAndGet());
        mealsData.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Meal getOne(Long id) {
        return mealsData.get(id);
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(mealsData.values());
    }

    @Override
    public Meal update(Meal entity) {
        Meal meal = mealsData.get(entity.getId());
        synchronized (meal){
            meal.setDateTime(entity.getDateTime());
            meal.setDescription(entity.getDescription());
            meal.setCalories(entity.getCalories());
        }
        return meal;
    }

    @Override
    public void deleteById(Long id) {
        mealsData.remove(id);
    }

    private void addSomeDataToDb() {
        save(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        save(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        save(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        save(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        save(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }
}
