package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
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

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> resultList = new ArrayList<>();
        Map<LocalDate, Integer> mapDateInt = new HashMap<>();
        int sum = 0;
        LocalDate date = meals.get(0).getDateTime().toLocalDate();
        for (UserMeal element : meals) {
            if (date.equals(element.getDateTime().toLocalDate())) {
                sum += element.getCalories();
            } else {
                sum = 0;
                sum += element.getCalories();
                date = element.getDateTime().toLocalDate();
            }
            mapDateInt.put(element.getDateTime().toLocalDate(), sum);
        }
        for (UserMeal element : meals) {
            if (TimeUtil.isBetweenHalfOpen(element.getDateTime().toLocalTime(), startTime, endTime) && mapDateInt.get(element.getDateTime().toLocalDate()) > caloriesPerDay) {
                resultList.add(new UserMealWithExcess(element.getDateTime(), element.getDescription(), element.getCalories(), true));
            } else if (TimeUtil.isBetweenHalfOpen(element.getDateTime().toLocalTime(), startTime, endTime)) {
                resultList.add(new UserMealWithExcess(element.getDateTime(), element.getDescription(), element.getCalories(), false));
            }
        }
        return resultList;
    }


    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        
        return null;
    }
}
