package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * Created by skorpion on 11.09.16.
 */
public class MealDB implements DAO {

    private static MealDB ourInstance = new MealDB();

    public static MealDB getInstance() {
        return ourInstance;
    }

    private MealDB() {
    }


    private static List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
            new Meal(LocalDateTime.of(2015, Month.APRIL, 5, 0, 0), "Завтрак", 510),
            new Meal(LocalDateTime.of(2015, Month.APRIL, 5, 12, 0), "Обед", 510),
            new Meal(LocalDateTime.of(2015, Month.APRIL, 5, 23, 59), "Ужин", 510)
    );

    public static List<Meal> getMeals() {
        return meals;
    }


    public List<MealWithExceed> getAllMealsWithExceed() {

        return MealsUtil.getFilteredWithExceeded(getMeals(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
    }


    @Override
    public void delete(Object object) {

        Meal meal = (Meal) object;


        // meals.remove(getMealOverId(meal.getId()));
        meals.remove(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));

    }

    public Meal getMealOverId(int id) {
        for (Meal meal : meals) {
            if (meal.getId() == id) {
                return meal;
            }
        }

        return null;
    }

}
