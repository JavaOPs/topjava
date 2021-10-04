package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
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
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcesses = null;
        for (UserMeal meal : meals) {

            String description = meal.getDescription();
            int cal = meal.getCalories();
            boolean excess = false;
            LocalDateTime localDateTime = meal.getDateTime();
            LocalTime time = meal.getDateTime().toLocalTime();

            for (UserMeal meal2 : meals) {
                LocalDate date = meal.getDateTime().toLocalDate();
                LocalDate date2 = meal2.getDateTime().toLocalDate();
                if (date.getDayOfMonth() == date2.getDayOfMonth()) {
                    int sumCal = cal + meal2.getCalories();
                    if (sumCal > caloriesPerDay) {
                        excess = true;
                    }
                }
                if (time.isAfter(startTime) && time.isBefore(endTime)) {
                    userMealWithExcesses = Arrays.asList(new UserMealWithExcess(localDateTime, description, cal, excess));
                }
            }
        }
        return userMealWithExcesses;
    }


    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
     Stream<UserMeal> stream = meals.stream();
     stream.filter(s -> s.getDateTime().toLocalTime().isAfter(startTime));
     stream.filter(s -> s.getDateTime().toLocalTime().isBefore(endTime));
        return (List<UserMealWithExcess>) stream;
    }
}
