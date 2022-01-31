package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.*;
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

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();

        // calc the sum for each day of receiving calories
        Map<LocalDate,Integer> impCaloriesPerDay = new HashMap<>();
        for (UserMeal userMeal : meals) {
            LocalDate localDateMeal = userMeal.getDateTime().toLocalDate();
            if (impCaloriesPerDay.containsKey(localDateMeal)) {
                impCaloriesPerDay.put(localDateMeal, impCaloriesPerDay.get(localDateMeal) + userMeal.getCalories());
            } else {
                impCaloriesPerDay.put(localDateMeal, userMeal.getCalories());
            }

        }

        // Collecting a collection
        for (UserMeal userMeal : meals) {
            LocalTime timeMeal = userMeal.getDateTime().toLocalTime();
            if (timeMeal.isAfter(startTime) && timeMeal.isBefore(endTime)) {
                userMealWithExcessList.add(
                        new UserMealWithExcess(
                                userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                impCaloriesPerDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)
                );
            }
        }

        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> impCaloriesPerDay =
                meals.parallelStream()
                        .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate()))
                        .entrySet().parallelStream()
                        .collect(Collectors.toMap(Map.Entry::getKey, localDateListEntry -> localDateListEntry.getValue().stream().mapToInt(UserMeal::getCalories).sum()));

        return meals.stream()
                .filter(userMeal -> userMeal.getDateTime().toLocalTime().isAfter(startTime) && userMeal.getDateTime().toLocalTime().isBefore(endTime))
                .sorted((um1,um2) -> um1.getDateTime().compareTo(um2.getDateTime()))
                .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), impCaloriesPerDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
