package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

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

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000); //default 7:00 to 12:00 cl 2000
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        // It works if this method get only a sorted by per day list meals

        List<UserMealWithExcess> resultMealList = new ArrayList<>();
        List<UserMeal> dayUserMeal = new ArrayList<>();
        int currentDay = 0;
        int dayCal = 0;

        for (UserMeal userMeal : meals) {
            LocalDateTime lcDateTime = userMeal.getDateTime();
            int day = lcDateTime.getDayOfMonth();
            if (currentDay != day) {
                currentDay = day;
                if (!dayUserMeal.isEmpty()) {
                    for (UserMeal dayMeal : dayUserMeal){
                        resultMealList.add(new UserMealWithExcess(dayMeal.getDateTime(), dayMeal.getDescription(), dayMeal.getCalories(), dayCal>caloriesPerDay));
                    }
                    dayUserMeal.clear();
                }
                dayCal = 0;
            }
            dayCal += userMeal.getCalories();
            if (TimeUtil.isBetweenHalfOpen(lcDateTime.toLocalTime(), startTime, endTime)) {
                dayUserMeal.add(userMeal);
            }
        }
        if (!dayUserMeal.isEmpty()) {
            for (UserMeal dayMeal : dayUserMeal){
                resultMealList.add(new UserMealWithExcess(dayMeal.getDateTime(), dayMeal.getDescription(), dayMeal.getCalories(), dayCal>caloriesPerDay));
            }
        }
        // TODO return filtered list with excess. Implement by cycles
        return resultMealList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
