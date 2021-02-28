package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
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
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> daysCalories = new HashMap<>();
        List<UserMealWithExcess> mealsTo = new ArrayList<>();

        for(UserMeal meal : meals) {
            LocalDate date = meal.getDateTime().toLocalDate();
            int totalCalories = daysCalories.getOrDefault(date, 0) + meal.getCalories();
            daysCalories.merge(date, totalCalories, (oldValue, newValue) -> newValue);
        }

        for(UserMeal meal : meals) {
            LocalTime time = meal.getDateTime().toLocalTime();

            int timeComparedStartTime = time.compareTo(startTime);
            int timeComparedEndTime = time.compareTo(endTime);

            if(timeComparedStartTime < 0 || timeComparedEndTime > 0) continue;

            LocalDateTime dateTime = meal.getDateTime();
            String description = meal.getDescription();
            int calories = meal.getCalories();

            LocalDate date = meal.getDateTime().toLocalDate();
            int caloriesReceived = daysCalories.get(date);

            boolean excess = caloriesReceived > caloriesPerDay;

            UserMealWithExcess userMealWithExcess = new UserMealWithExcess(dateTime, description, calories, excess);
            mealsTo.add(userMealWithExcess);
        }

        return mealsTo;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> daysCalories = meals
                .stream()
                .collect(Collectors.groupingBy(
                        UserMeal::getLocalDate,
                        Collectors.summingInt(UserMeal::getCalories)));

        List<UserMealWithExcess> mealsTo = meals
                .stream()
                .filter(meal ->
                        meal.getDateTime().toLocalTime().compareTo(startTime) >= 0 && meal.getDateTime().toLocalTime().compareTo(endTime) <= 0)
                .map(meal ->
                        new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                                daysCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
        return mealsTo;
    }
}
