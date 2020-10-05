package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealDaoImpl implements DaoInterface<Meal, Long> {
    private SomeDB connection;

    public MealDaoImpl(SomeDB connection) {
        this.connection = connection;
    }

    @Override
    public Meal save(Meal entity) {
        return connection.addMealToDb(entity);
    }

    @Override
    public Meal getOne(Long aLong) {
        return connection.getMealById(aLong);
    }

    @Override
    public List<Meal> findAll() {
        return connection.getAllDataFromDb();
    }

    @Override
    public Meal update(Meal entity) {
        return connection.updateMealInDb(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        connection.deleteMealFromDbById(aLong);
    }

    @Override
    public void delete(Meal entity) {
        connection.deleteMealFromDb(entity);
    }
}
