package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.repository.InMemoryRepository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.util.MealsUtil.meals;

public class MealsRepository implements InMemoryRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public MealsRepository() {
        meals.forEach(this::save);
    }

    public void save(Meal meal) {
        if(meal.getId() == null) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
        }
        repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    public void add(Meal meal) {
        save(meal);
    }

    public void update(Meal meal) {
        repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    public void delete(int id) {
        repository.remove(id);
    }

    public Collection<Meal> getAll() {
        return repository.values();
    }

    public Meal getById(int id) {
        return repository.get(id);
    }
}
