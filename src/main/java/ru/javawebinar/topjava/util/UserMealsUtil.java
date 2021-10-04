package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
        System.out.println(filteredByCollector(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesPerDays = new HashMap<>();
        meals.forEach(meal ->
                sumCaloriesPerDays.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));
        List<UserMealWithExcess> filteredUserMealsWithExcess = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredUserMealsWithExcess.add(new UserMealWithExcess(
                        meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        sumCaloriesPerDays.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        });
        return filteredUserMealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> daysCalories = meals.stream()
                .collect(Collectors
                        .toMap(meal -> meal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        daysCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static List<UserMealWithExcess> filteredByCollector(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final class MealsWithDaysCalories {
            private final List<UserMeal> daysMeals = new ArrayList<>();
            private final Map<LocalDate, Integer> daysCalories = new HashMap<>();
        }
        return meals.stream().collect(Collector.of(
                MealsWithDaysCalories::new,
                (mealsWithDaysCalories, userMeal) -> {
                    LocalDate currentDate = userMeal.getDateTime().toLocalDate();
                    if (currentDate != null) {
                        mealsWithDaysCalories.daysCalories.merge(currentDate, userMeal.getCalories(), Integer::sum);
                    }

                    if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                        mealsWithDaysCalories.daysMeals.add(userMeal);
                    }
                },
                (mealsWithDaysCalories1, mealsWithDaysCalories2) -> {
                    mealsWithDaysCalories1.daysMeals.addAll(mealsWithDaysCalories2.daysMeals);
                    mealsWithDaysCalories1.daysCalories.putAll(mealsWithDaysCalories2.daysCalories);
                    return mealsWithDaysCalories1;
                },
                mealsWithDaysCalories -> mealsWithDaysCalories.daysMeals.stream().map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        mealsWithDaysCalories.daysCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay)))).collect(toList());
    }
}
