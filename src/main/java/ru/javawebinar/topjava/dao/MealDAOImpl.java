package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Aspire on 07.12.2016.
 */
public class MealDAOImpl implements MealDAO {
    private static List<Meal> meals = MealsUtil.getMeals();

    private MealDAOImpl() {
    }

    public void addMeal(Meal meal){
        meals.add(meal);
    }

    @Override
    public void updateMeal(Meal meal) {

    }

    public Meal getMealById(int id){
        List<Meal>mealsById = meals
                .stream().filter(meal -> meal.getId()==id)
                .collect(Collectors.toList());

        if(mealsById.size()>0)
            return mealsById.get(0);
        else
            return null;
    }

    public void removeMeal(int id){
        meals.remove(getMealById(id));
    }

    public static List<Meal> getAllMeals() {
        return meals;
    }
}
