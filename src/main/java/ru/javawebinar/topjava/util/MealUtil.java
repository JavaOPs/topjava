package ru.javawebinar.topjava.util;


import ru.javawebinar.topjava.dto.MealWithExceed;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class MealUtil {

    private MealUtil() {}

    public static List<MealWithExceed> getFilteredWithExceeded(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> groupByDateCalories = mealList.stream()
                .collect(groupingBy(Meal::getDate, summingInt(Meal::getCalories)));
        return mealList.stream()
                .filter(meal -> TimeUtil.isBeetwen(meal.getTime(), startTime, endTime))
                .map(meal -> getMealWithExceed(meal, groupByDateCalories.get(meal.getDate()) > caloriesPerDay))
                .collect(toList());
    }

    private static MealWithExceed getMealWithExceed(Meal meal, boolean exceed) {
        return new MealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed);
    }
}