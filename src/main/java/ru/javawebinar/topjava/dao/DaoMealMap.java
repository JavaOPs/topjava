package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by anikiforova on 27.03.2017.
 */
public class DaoMealMap implements DaoInterface<Meal> {

    private Map<Integer, Meal> map;
    private static AtomicInteger currentId = new AtomicInteger(-1);

    public DaoMealMap(Map<Integer, Meal> map) {
        this.map = map;
    }

    @Override
    public void add(Meal meal){
        meal.setId(currentId.incrementAndGet());
        map.put(currentId.get(), meal);
    }

    @Override
    public void delete(int id){
        map.remove(id);
    }

    @Override
    public void update(Meal meal) {
        map.replace(meal.getId(), meal);
    }

    @Override
    public Meal getById(int id) {
        return map.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }
}
