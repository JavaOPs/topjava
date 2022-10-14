package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

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

import static java.util.stream.Collectors.summingInt;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 500)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(14, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(
            List<UserMeal> meals,
            LocalTime startTime,
            LocalTime endTime,
            int caloriesPerDay
    ) {
        Map<LocalDate, Integer> caloriesByDays = new HashMap<>();
        meals.forEach(m -> caloriesByDays.merge(m.getDateTime().toLocalDate(), m.getCalories(), Integer::sum));
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            LocalTime time = userMeal.getDateTime().toLocalTime();
            if (TimeUtil.isBetweenHalfOpen(time, startTime, endTime)) {
                userMealWithExcesses.add(new UserMealWithExcess(
                        userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        caloriesByDays.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay
                ));
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(
            List<UserMeal> meals,
            LocalTime startTime,
            LocalTime endTime,
            int caloriesPerDay
    ) {
        Map<LocalDate, Integer> caloriesDay =
                meals.stream()
                     .collect(Collectors.groupingBy(
                             userMeal -> userMeal.getDateTime().toLocalDate(),
                             summingInt(UserMeal::getCalories)
                     ));
        return meals.stream()
                    .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal .getDateTime().toLocalTime(), startTime, endTime))
                    .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                            caloriesDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay
                    ))
                    .collect(Collectors.toList());
    }
}
