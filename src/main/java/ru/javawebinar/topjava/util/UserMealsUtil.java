package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserMealsUtil {

    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Breakfast", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Lunch", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Dinner", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Breakfast", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Lunch", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Dinner", 500)
        );
        List<UserMealWithExceed> filteredMealsWithExceeded = getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        //.toLocalDate(); --what for
        //.toLocalTime(); --what for
    }

    //TODO
    static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime start, LocalTime end, int cal) {
        //checking is time in between limits
        List<UserMeal> filteredMeals = mealList.stream()
                                               .filter(getFilterPredicate(start, end))
                                               .collect(Collectors.toList());

        boolean exceed = isExceed(cal, filteredMeals);

        return filteredMeals.stream()
                            .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed))
                            .collect(Collectors.toList());
    }

    private static Predicate<UserMeal> getFilterPredicate(LocalTime start, LocalTime end) {
        return meal -> {
            LocalDateTime dateTime = meal.getDateTime();
            return dateTime.isAfter(ChronoLocalDateTime.from(start)) && dateTime.isBefore(ChronoLocalDateTime.from(end));
        };
    }

    static boolean isExceed(int cal, List<UserMeal> userMeals) {
        //checking how many calories for all meals
        int caloriesPerDay = userMeals.stream()
                                      .map(UserMeal::getCalories)
                                      .mapToInt(i -> i)
                                      .sum();
        //checking is it more that filter params
        return caloriesPerDay > cal;
    }
}
