package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),

                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 10)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalDateTime.of(2020, 1, 30, 7, 0),
                LocalDateTime.of(2020, 1, 31, 20, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalDateTime.of(2020, 1, 30, 7, 0),
                LocalDateTime.of(2020, 1, 31, 20, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalDateTime startTime, LocalDateTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> list = new ArrayList<>();
        int[] calories = {0};
        for (UserMeal meal : meals) {
            if (meal.getDateTime().compareTo(startTime) > 0 && meal.getDateTime().compareTo(endTime) <= 0) {
                calories[0] += meal.getCalories();
                list.add(new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        calories[0] >= caloriesPerDay));
            }
        }
        return list;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalDateTime startTime, LocalDateTime endTime, int caloriesPerDay) {
        AtomicInteger acc = new AtomicInteger();
        return meals.stream()
                .filter(s -> s.getDateTime().compareTo(startTime) > 0 && s.getDateTime().compareTo(endTime) <= 0)
                .map(x -> {
                    acc.addAndGet(x.getCalories());
                    return new UserMealWithExcess(x.getDateTime(), x.getDescription(), x.getCalories(), acc.get() >= caloriesPerDay);
                })
                .collect(Collectors.toList());
    }


}