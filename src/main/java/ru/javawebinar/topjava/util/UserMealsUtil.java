package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 28, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 28, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 28, 20, 0), "Ужин", 800),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 29, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 29, 10, 0), "Завтрак", 607),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 29, 13, 0), "Обед", 200),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 29, 20, 0), "Ужин", 410),
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

        System.out.println("==========================================");
        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> mealsTo = new ArrayList<>();
        Map<LocalDate, Integer> mapa = new HashMap<>();

        for (UserMeal userMeal : meals) {
            if (mapa.containsKey(userMeal.getDateTime().toLocalDate())) {
                mapa.put(userMeal.getDateTime().toLocalDate(), mapa.get(userMeal.getDateTime().toLocalDate()) + userMeal.getCalories());
            } else {
                mapa.put(userMeal.getDateTime().toLocalDate(), userMeal.getCalories());
            }
        }

        for (UserMeal userMeal : meals) {
            if (isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                if (mapa.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay) {
                    mealsTo.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false));
                } else {
                    mealsTo.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true));
                }
            }
        }

        return mealsTo;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapa = new HashMap<>();

        meals.forEach(meal -> {
            LocalDate localDate = meal.getDateTime().toLocalDate();
            if (mapa.containsKey(localDate)) {
                mapa.put(localDate, mapa.get(localDate) + meal.getCalories());
            } else {
                mapa.put(localDate, meal.getCalories());
            }
        });

        return meals
                .stream()
                .filter(userMeal -> isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> {
                    // туть?
                    if (mapa.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay) {
                        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false);
                    } else {
                        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true);
                    }
                }).collect(Collectors.toList());
    }
}
