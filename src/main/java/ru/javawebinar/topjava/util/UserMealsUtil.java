package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

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

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> resultArray = new ArrayList<>();

        List<UserMeal> rightTimeMeals = new ArrayList<>();

        Map<LocalDate, Integer> dayCaloriesMap = new HashMap<>();

        for (UserMeal m:meals) {

            LocalDate mealDay = m.getDateTime().toLocalDate();

            dayCaloriesMap.merge(mealDay,m.getCalories(), Integer::sum);

            if (TimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalTime(), startTime, endTime)){
                rightTimeMeals.add(m);
            }
        }

        for (UserMeal m: rightTimeMeals) {
            resultArray.add(new UserMealWithExcess(m.getDateTime(),m.getDescription(),m.getCalories(),dayCaloriesMap.get(m.getDateTime().toLocalDate()) > caloriesPerDay));
        }

        return resultArray;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> dayCaloriesMap = new HashMap<>();

        List<UserMeal> rightTime = new ArrayList<>();

        meals.forEach(m -> {
                    LocalDate mealDay = m.getDateTime().toLocalDate();

                    dayCaloriesMap.merge(mealDay,m.getCalories(), Integer::sum);

                    if (TimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalTime(), startTime, endTime)){
                        rightTime.add(m);
                    }
                });

        return rightTime.stream()
                .map(m -> new UserMealWithExcess(m.getDateTime(),m.getDescription(),m.getCalories(),dayCaloriesMap.get(m.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}


