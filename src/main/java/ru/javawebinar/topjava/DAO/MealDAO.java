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
public class MealDAO implements DAO{

    private static MealDAO ourInstance = new MealDAO();

    public static MealDAO getInstance() {
        return ourInstance;
    }

    private MealDAO() {
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

        return MealsUtil.getFilteredWithExceeded(getMeals(), LocalTime.of(0,0),LocalTime.of(23,59),2000);
    }

}
