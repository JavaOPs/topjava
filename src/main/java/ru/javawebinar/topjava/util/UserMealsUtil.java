package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.MARCH, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.MARCH, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.MARCH, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.MARCH, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.MARCH, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.MARCH, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.MARCH, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsToCycles = filteredByCycles(meals,
                LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToCycles.forEach(System.out::println);
        System.out.println("8---------------8");
        List<UserMealWithExcess> mealsToStreams = filteredByStreams(meals,
                LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToStreams.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapForCaloriesPerDay = new HashMap<>();
        for (UserMeal userMealForMap : meals) {
            LocalDate dateTimeOfMeal = userMealForMap.getDateTime().toLocalDate();
            mapForCaloriesPerDay.merge(dateTimeOfMeal, userMealForMap.getCalories(), Integer::sum);
        }
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                boolean checkedPerDay = (mapForCaloriesPerDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay);
                UserMealWithExcess element = new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                        userMeal.getCalories(), checkedPerDay);
                userMealWithExcesses.add(element);
            }
        }
        return userMealWithExcesses;
    }
    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapForCaloriesPerDay = meals.stream()
                .collect(Collectors.toMap(userMeal -> userMeal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum, HashMap::new));

        return meals.stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> (new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        (mapForCaloriesPerDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))))
                .collect(Collectors.toList());
    }
}
