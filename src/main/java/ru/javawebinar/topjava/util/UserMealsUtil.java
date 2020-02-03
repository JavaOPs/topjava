package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.summingInt;

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

        List<UserMealWithExcess> mealsToWithoutMap = filteredByCyclesWithoutMap(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<UserMeal, Boolean> userMealsWithExcess = new HashMap<>();
        List<UserMealWithExcess> userMealsWithExcessToArray = new ArrayList<>();
        int caloriesPerPeriodReal = 0;
        for (UserMeal meal : meals) {
            if (meal.getDateTime().toLocalTime().isAfter(startTime) && meal.getDateTime().toLocalTime().isBefore(endTime)) {
                userMealsWithExcess.put(meal, false);
                caloriesPerPeriodReal += meal.getCalories();
            }
        }
        for (Map.Entry<UserMeal, Boolean> meal : userMealsWithExcess.entrySet()) {
            UserMealWithExcess mealWithExcess = new UserMealWithExcess(meal.getKey(),
                    caloriesPerPeriodReal > caloriesPerDay);
            userMealsWithExcessToArray.add(mealWithExcess);
        }


        return userMealsWithExcessToArray;
    }


    public static List<UserMealWithExcess> filteredByCyclesWithoutMap(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> userMealsWithExcess = new ArrayList<>();
        int caloriesPerPeriodReal = 0;
        for (UserMeal meal : meals) {
            if (meal.getDateTime().toLocalTime().isAfter(startTime) && meal.getDateTime().toLocalTime().isBefore(endTime)) {
                caloriesPerPeriodReal += meal.getCalories();
            }
        }
        if (caloriesPerPeriodReal > caloriesPerDay) {
            for (UserMeal meal : meals) {
                if (meal.getDateTime().toLocalTime().isAfter(startTime) && meal.getDateTime().toLocalTime().isBefore(endTime)) {
                    UserMealWithExcess userMealWithExcessElement = new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), true);
                    userMealsWithExcess.add(userMealWithExcessElement);
                }
            }
        } else {
            for (UserMeal meal : meals) {
                if (meal.getDateTime().toLocalTime().isAfter(startTime) && meal.getDateTime().toLocalTime().isBefore(endTime)) {
                    UserMealWithExcess userMealWithExcessElement = new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), false);
                    userMealsWithExcess.add(userMealWithExcessElement);
                }
            }
        }
        return userMealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Integer sum = meals.stream()
                .filter(s -> s.getDateTime().toLocalTime().isAfter(startTime) && s.getDateTime().toLocalTime().isBefore(endTime))
                .mapToInt(s -> s.getCalories()).sum();
        List<UserMealWithExcess> streamFromCollection = meals.stream()
                .filter(s -> s.getDateTime().toLocalTime().isAfter(startTime) && s.getDateTime().toLocalTime().isBefore(endTime))
                .map(p -> new UserMealWithExcess(p.getDateTime(), p.getDescription(), p.getCalories(), sum < caloriesPerDay))
                .collect(Collectors.toList());

        /* .mapToInt(s -> s.getCalories()).sum()
           .collect(Collectors.groupingBy(UserMeal::, summingInt(UserMeal::getCalories)))*/
        /*.map(s -> new UserMealWithExcess(s.getDateTime(), s.getDescription(), s.getCalories(),(UserMeal::getCalories). caloriesPerDay))*/
        /*  .mapToInt(s -> s.getCalories()).sum() > caloriesPerDay*/
        /* .collect(Collectors.toList(s -> s.getDateTime(), s -> s.getDescription(), s -> s.getCalories(), false));
        Map<UserMeal, Integer> sum = items.stream().collect(
                Collectors.groupingBy(Item::getName, Collectors.summingInt(Item::getQty)));*/

        return streamFromCollection;
    }
}
