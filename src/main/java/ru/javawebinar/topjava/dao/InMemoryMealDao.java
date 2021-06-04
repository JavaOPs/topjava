package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryMealDao implements DaoInterface<Meal, Long> {
    private final Map<Long, Meal> mealsData = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong();

    public InMemoryMealDao() {
        addSomeDataToDb();
    }

    @Override
    public Meal save(Meal entity) {
        if(entity.getId() == null){
            entity.setId(id.incrementAndGet());
        }
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
    public void deleteById(Long id) {
        mealsData.remove(id);
    }

    private void addSomeDataToDb() {
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }
}
