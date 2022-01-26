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

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000); //default 7:00 to 12:00 cl 2000
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();

        for (UserMeal userMeal : meals) {
            LocalDate dayDate = userMeal.getDateTime().toLocalDate();
            if (caloriesPerDayMap.containsKey(dayDate)) {
                caloriesPerDayMap.put(dayDate, caloriesPerDayMap.get(dayDate) + userMeal.getCalories());
            } else caloriesPerDayMap.put(dayDate, userMeal.getCalories());
        }

        List<UserMealWithExcess> resultMealList = new ArrayList<>();

        for (UserMeal userMeal : meals) {
            LocalDateTime lcDateTime = userMeal.getDateTime();
            if (TimeUtil.isBetweenHalfOpen(lcDateTime.toLocalTime(), startTime, endTime)) {
                resultMealList.add(new UserMealWithExcess(lcDateTime, userMeal.getDescription(), userMeal.getCalories(),
                        caloriesPerDayMap.get(lcDateTime.toLocalDate()) > caloriesPerDay));
            }
        }
        return resultMealList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesPerDayMap = meals.stream()
                .collect(Collectors.toMap(um -> um.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        return meals.stream()
                .filter(um -> TimeUtil.isBetweenHalfOpen(um.getDateTime().toLocalTime(), startTime, endTime))
                .map((um -> new UserMealWithExcess(um.getDateTime(), um.getDescription(), um.getCalories(),
                        caloriesPerDayMap.get(um.getDateTime().toLocalDate()) > caloriesPerDay)))
                .collect(Collectors.toList());
    }
}