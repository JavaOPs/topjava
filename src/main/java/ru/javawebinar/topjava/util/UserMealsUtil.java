package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
        System.out.println();
        filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesMap = getCaloriesPerDayMap(meals);
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        for (UserMeal meal: meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExcessList.add(
                    new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), isExcess(caloriesMap, meal, caloriesPerDay)));
            }
        }
        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesMap = getCaloriesPerDayMap(meals);
        return meals.stream()
            .filter(m -> TimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalTime(), startTime, endTime))
            .map(m -> new UserMealWithExcess(m.getDateTime(), m.getDescription(), m.getCalories(), isExcess(caloriesMap, m, caloriesPerDay)))
            .collect(Collectors.toList());
    }

    private static Map<LocalDate, Integer> getCaloriesPerDayMap(List<UserMeal> meals) {
        final Map<LocalDate, Integer> caloriesMap = new HashMap<>();
        meals.forEach(m -> caloriesMap.merge(m.getDateTime().toLocalDate(), m.getCalories(), Integer::sum));
        return caloriesMap;
    }

    private static boolean isExcess(Map<LocalDate, Integer> caloriesMap, UserMeal meal, int caloriesPerDay) {
        return caloriesMap.getOrDefault(meal.getDateTime().toLocalDate(), 0) > caloriesPerDay;
    }
}
