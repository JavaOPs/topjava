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
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500), //+
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000), //+
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> listResult = new ArrayList<>();
        int sumCurrent = 0;
        LocalDate dateCurrent = meals.get(0).getDateTime().toLocalDate();
        for(UserMeal element : meals) {
            LocalDate elementDate = element.getDateTime().toLocalDate();
            if (elementDate.equals(dateCurrent)) {
                sumCurrent += element.getCalories();
                if (TimeUtil.isBetweenHalfOpen(element.getDateTime().toLocalTime(), startTime, endTime)) {
                    listResult.add(new UserMealWithExcess(element.getDateTime(), element.getDescription(), element.getCalories(), false));
                }
            } else if (!elementDate.equals(dateCurrent)) {
                sumCurrent = 0;
                dateCurrent = element.getDateTime().toLocalDate();
                sumCurrent += element.getCalories();
                if (TimeUtil.isBetweenHalfOpen(element.getDateTime().toLocalTime(), startTime, endTime)) {
                    listResult.add(new UserMealWithExcess(element.getDateTime(), element.getDescription(), element.getCalories(), false));
                }
            }
             if (sumCurrent > caloriesPerDay) {
                 LocalDate finalDateCurrent = dateCurrent;
                 listResult.forEach(elem -> {
                     if (elem.getDateTime().toLocalDate().equals(finalDateCurrent)) {
                         elem.setExcess(true);
                     }
                 });
            }
        }
        return listResult;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
