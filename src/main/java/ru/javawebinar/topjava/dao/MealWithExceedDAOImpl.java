package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Aspire on 07.12.2016.
 */
public class MealWithExceedDAOImpl implements MealWithExceedDAO {
    private static List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(MealDAOImpl.getAllMeals(), null, null, 2000);

    private MealWithExceedDAOImpl() {
    }

    @Override
    public void updateMeal(MealWithExceed meal) {

    }

    public static MealWithExceed getMealById(int id){
        List<MealWithExceed>mealsById = meals
                .stream().filter(meal -> meal.getId()==id)
                .collect(Collectors.toList());

        if(mealsById.size()>0)
            return mealsById.get(0);
        else
            return null;
    }

    public static void removeMeal(int id){
        meals.remove(getMealById(id));
    }

    public static List<MealWithExceed> getAllMeals() {
        return meals;
    }

    public static void addMeal(MealWithExceed meal){
        meals.add(meal);
    }
}
