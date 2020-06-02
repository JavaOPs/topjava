package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
//        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> usersMealWithExcesses = new ArrayList<>();
        List<UserMeal> userMeals = new ArrayList<>();
        Map<LocalDate, Integer> dateTimeMapWithCalories = new HashMap<>();
        Date dateStart = new Date();

        for (UserMeal userMeal : meals) {
            dateTimeMapWithCalories.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), (a, b) -> b + a);
            if (userMeal.getDateTime().toLocalTime().isAfter(startTime) && userMeal.getDateTime().toLocalTime().isBefore(endTime)) {
                userMeals.add(userMeal);
            }
        }

        for (UserMeal userMeal : userMeals) {
            boolean excess = dateTimeMapWithCalories.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay;
            usersMealWithExcesses.add(new UserMealWithExcess(userMeal.getDateTime(),userMeal.getDescription(), userMeal.getCalories(), excess));
        }

        Date dateFinish = new Date();
        System.out.println(dateFinish.getTime() - dateStart.getTime());

        return usersMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Date dateStart = new Date();
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        List<UserMeal> userMeals = new ArrayList<>();
        Map<LocalDate, Integer> dateTimeMapWithCalories = new HashMap<>();

        List<UserMeal> result = meals.stream()
                .filter((time) -> time.getDateTime().toLocalTime().isAfter(startTime) && time.getDateTime().toLocalTime().isBefore(endTime)).collect(Collectors.toList());

        for (UserMeal userMeal : result) {
            userMealWithExcessList.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false));
        }

        Date dateEnd = new Date();
        System.out.println(dateEnd.getTime() - dateStart.getTime());
        return userMealWithExcessList;
    }
}
