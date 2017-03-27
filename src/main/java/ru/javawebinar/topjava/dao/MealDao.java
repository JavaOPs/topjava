package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

/**
 * Created by anikiforova on 27.03.2017.
 */
public class MealDao {
    public void editOrAdd(Meal meal){
        meal.setId(MealsUtil.currentId);
        MealsUtil.meals.put(MealsUtil.currentId++, meal);
    }

    public void delete(int id){
        MealsUtil.meals.remove(id);
    }

    public void update(Meal meal) {
        MealsUtil.meals.replace(meal.getId(), meal);
    }
}
