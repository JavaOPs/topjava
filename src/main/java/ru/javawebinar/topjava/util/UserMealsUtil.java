package ru.javawebinar.topjava.util;

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

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

public class UserMealsUtil {
    private static List<UserMeal> mealList = Arrays.asList(
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Breakfast", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Dinner", 1000),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Supper", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Breakfast", 1000),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Dinner", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Supper", 510)
    );

    public static void main(String[] args) {
        List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceededCycle(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        filteredWithExceeded.forEach(System.out::println);
        System.out.println();
        filteredWithExceeded = getFilteredWithExceededLambda(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        filteredWithExceeded.forEach(System.out::println);
        System.out.println();
        filteredWithExceeded = getFilteredWithExceededLambda2(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        filteredWithExceeded.forEach(System.out::println);
//        .toLocalDate();
//        .toLocalTime();

    }

    private static List<UserMealWithExceed> getFilteredWithExceededCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> userMealsWithExceeds = new ArrayList<>();
        Map<LocalDate, List<UserMeal>> userMealByDay = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            if (userMealByDay.containsKey(localDate)) {
                userMealByDay.get(localDate).add(userMeal);
            } else {
                List<UserMeal> meals = new ArrayList<>();
                meals.add(userMeal);
                userMealByDay.put(localDate, meals);
            }
        }
        for (Map.Entry<LocalDate, List<UserMeal>> pair : userMealByDay.entrySet()) {
            List<UserMeal> value = pair.getValue();
            boolean exceeds = false;
            int calories = 0;
            for (UserMeal userMeal : value) {
                calories += userMeal.getCalories();
                if (calories > caloriesPerDay) {
                    exceeds = true;
                }
            }
            for (UserMeal userMeal : value) {
                userMealsWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(),
                        userMeal.getDescription(), userMeal.getCalories(), exceeds));
            }
        }
        return userMealsWithExceeds.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    private static List<UserMealWithExceed> getFilteredWithExceededLambda(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, List<UserMeal>> collect = mealList.stream().collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate()));
        List<UserMealWithExceed> mealsWithExceed = new ArrayList<>();
        collect.keySet().forEach(localDate -> {
            List<UserMeal> userMeals = collect.get(localDate);
            int calories = userMeals.stream().mapToInt(UserMeal::getCalories).sum();
            List<UserMealWithExceed> collect1 = userMeals.stream()
                    .map(m -> new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), calories > caloriesPerDay))
                    .collect(Collectors.toList());
            mealsWithExceed.addAll(collect1);
        });
        return mealsWithExceed.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    private static List<UserMealWithExceed> getFilteredWithExceededLambda2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream().collect(
                Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(),
                        meal.getCalories(), caloriesSumByDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
