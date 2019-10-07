package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class Repo implements DBManager{
    private static final List<Meal> mealToStorage = new ArrayList<>();


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

    @Override
    public void get(Integer id) {
        mealToStorage.get(id);
    }
}
