package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<UserMealWithExcess> usersMealWithExcesses = new ArrayList<>();
        List<UserMeal> userMeals = new ArrayList<>();
        Map<LocalDate, Integer> dateTimeMapWithCalories = new HashMap<>();

        for (UserMeal meal : meals) {
            dateTimeMapWithCalories.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), (a, b) -> b + a);
            if (meal.getDateTime().toLocalTime().isAfter(startTime) && meal.getDateTime().toLocalTime().isBefore(endTime)) {
                userMeals.add(meal);
            }
        }

        for (UserMeal userMeal : userMeals) {
            boolean excess = dateTimeMapWithCalories.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay;
            usersMealWithExcesses.add(new UserMealWithExcess(userMeal.getDateTime(),userMeal.getDescription(), userMeal.getCalories(), excess));
        }

        return usersMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        List<UserMeal> userMeals;

        Map<LocalDate, Integer> dateTimeMapWithCalories = meals.stream()
                .collect(Collectors.toMap(k -> k.getDateTime().toLocalDate(), v -> v.getCalories(), (v1, v2) -> (v1 + v2), HashMap::new));

        userMeals = meals.stream()
                .filter((time) -> time.getDateTime().toLocalTime().isAfter(startTime) && time.getDateTime().toLocalTime().isBefore(endTime)).collect(Collectors.toList());

        userMeals.forEach(userMeal -> userMealWithExcessList.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),dateTimeMapWithCalories.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)));

        return userMealWithExcessList;
    }
}
