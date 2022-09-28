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

        List<UserMealWithExcess> mealsToCycles = filteredByCycles(meals, LocalTime.of(0, 0), LocalTime.of(21, 0), 1000);
        mealsToCycles.forEach(System.out::println);
        System.out.println("8---------------8");
        List<UserMealWithExcess> mealsToStreams = filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(21, 0), 1000);
        mealsToStreams.forEach(System.out::println);


    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMeal> filteredMeals = new ArrayList<>();
        Map<LocalDate, Integer> mapForCaloriesPerDay = new HashMap<>();
        for (UserMeal mUserMeal : meals) {
            boolean checkedTime = (mUserMeal.getDateTime().toLocalTime().isAfter(startTime)
                    && mUserMeal.getDateTime().toLocalTime().isBefore(endTime));
            if (checkedTime) {
                filteredMeals.add(mUserMeal);
            }
            if (checkedTime && !mapForCaloriesPerDay.containsKey(LocalDate.ofEpochDay(mUserMeal.getDateTime().getDayOfMonth()))) {
                mapForCaloriesPerDay.put(LocalDate.ofEpochDay(mUserMeal.getDateTime().getDayOfMonth()), mUserMeal.getCalories());
            } else if (checkedTime) {
                mapForCaloriesPerDay.replace(LocalDate.ofEpochDay(mUserMeal.getDateTime().getDayOfMonth()),
                        (mapForCaloriesPerDay.get(LocalDate.ofEpochDay(mUserMeal.getDateTime().getDayOfMonth())) + mUserMeal.getCalories()));
            }

        }
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal userMeal : filteredMeals) {
            boolean checkedPerDay = mapForCaloriesPerDay.get(LocalDate.ofEpochDay(userMeal.getDateTime().getDayOfMonth())) > caloriesPerDay;
            UserMealWithExcess element = new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                    userMeal.getCalories(), checkedPerDay);
            userMealWithExcesses.add(element);
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        List<UserMeal> userMealList = meals.stream().filter(mUserMeal -> mUserMeal.getDateTime().toLocalTime().isBefore(endTime) &&
                mUserMeal.getDateTime().toLocalTime().isAfter(startTime)).collect(Collectors.toList());

        Map<LocalDate, Integer> filteredMeals = userMealList.stream().
                collect(Collectors.groupingBy(UserM -> LocalDate.ofEpochDay(UserM.getDateTime().getDayOfMonth()),
                        Collectors.summingInt(UserMeal::getCalories)));


        return userMealList.stream().map(userMeal -> {
                    return (new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                            (filteredMeals.get(LocalDate.ofEpochDay(userMeal.getDateTime().getDayOfMonth())) > caloriesPerDay)));
                }).
                collect(Collectors.toList());
    }
}
