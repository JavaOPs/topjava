package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> exceeded = getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println(exceeded.toString());
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> result = new ArrayList<>();

        toLocalDate(mealList).forEach(
                (localDate, userMeals) -> {
                    Stream<UserMeal> meals = userMeals.stream();
                    Integer consumedCalories = userMeals.stream().collect(Collectors.summingInt(UserMeal::getCalories));

                    List<UserMealWithExceed> collectedMeal =
                            toLocalTime(startTime, endTime, userMeals)
                                    .map(meal -> new UserMealWithExceed(
                                            meal.getDateTime(),
                                            meal.getDescription(),
                                            meal.getCalories(), consumedCalories > caloriesPerDay))
                                    .collect(Collectors.toList());

                    result.addAll(collectedMeal);
                }
        );
        return result;
    }

    private static Stream<UserMeal> toLocalTime(LocalTime startTime, LocalTime endTime, List<UserMeal> meals) {
        return meals.stream()
                .filter(userMeal -> LocalTime.from(userMeal.getDateTime()).compareTo(startTime) >= 0 && LocalTime.from(userMeal.getDateTime()).compareTo(endTime) <= 0);
    }

    private static Map<LocalDate, List<UserMeal>> toLocalDate(List<UserMeal> mealList) {
        return mealList.stream()
                .collect(Collectors.groupingBy(UserMeal -> LocalDate.from(UserMeal.getDateTime())));
    }


}
