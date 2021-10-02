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

    public static Boolean isBetweenBeforeAndAfter(LocalTime current, LocalTime before, LocalTime after) {
        return current.isAfter(after) && current.isBefore(before);
    }


    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        Map<LocalDate, Integer> sumCaloriesPerDay = new HashMap<>();
        meals.forEach(meal ->
                sumCaloriesPerDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));
        List<UserMealWithExcess> filteredUserMealWithEx = new ArrayList<>();
        meals.forEach(meal -> {
            if (isBetweenBeforeAndAfter(meal.getDateTime().toLocalTime(), endTime, startTime)) {
                filteredUserMealWithEx.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), sumCaloriesPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        });
        return filteredUserMealWithEx;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        Map<LocalDate, Integer> daysCalories = meals.stream()
                .collect(Collectors
                        .toMap(UserMeal -> UserMeal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));
        return meals.stream()
                .filter(meal -> isBetweenBeforeAndAfter(meal.getDateTime().toLocalTime(), endTime, startTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), daysCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }


    private static List<UserMealWithExcess> filteredByCollector(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final class mealsWithDaysCalories {
            private final List<UserMeal> daysMeals = new ArrayList<>();
            private int daysCalories;
        }
        return meals.stream().collect(Collector.of(
                mealsWithDaysCalories::new,
                (j, p) -> {
                    j.daysCalories += p.getCalories();
                    if (isBetweenBeforeAndAfter(p.getDateTime().toLocalTime(), endTime, startTime)) {
                        j.daysMeals.add(p);

                    }
                },
                (j1, j2) -> {
                    j1.daysCalories += j2.daysCalories;
                    j1.daysMeals.addAll(j2.daysMeals);
                    return j1;
                },
                j -> j.daysMeals.stream().map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), j.daysCalories > caloriesPerDay)))).collect(toList());

    }


}
