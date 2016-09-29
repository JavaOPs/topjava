package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.util.model.UserMeal;
import ru.javawebinar.topjava.util.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xcoder on 09/04/16.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2016, Month.SEPTEMBER, 4, 11, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2016, Month.SEPTEMBER, 4, 13, 0), "Обед", 1100),
                new UserMeal(LocalDateTime.of(2016, Month.SEPTEMBER, 4, 19, 0), "Ужин", 700),
                new UserMeal(LocalDateTime.of(2016, Month.SEPTEMBER, 5, 10, 0), "Завтрак", 600),
                new UserMeal(LocalDateTime.of(2016, Month.SEPTEMBER, 5, 15, 0), "Обед", 1400),
                new UserMeal(LocalDateTime.of(2016, Month.SEPTEMBER, 5, 20, 0), "Ужин", 800)
                );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        //        .toLocalDate();
        //        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                     LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        return null;
    }
}
