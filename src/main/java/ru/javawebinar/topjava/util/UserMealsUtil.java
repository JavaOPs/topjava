package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.List;
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

 //       List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2005);
   //     mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles

        Map<LocalDate, Integer> temp = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate key = meal.getDateTime().toLocalDate();
            if(temp.containsKey(key))
                 temp.computeIfPresent(key, (k, v) -> meal.getCalories()+v);
            else temp.put(key, meal.getCalories());

       }
        List<UserMealWithExcess> resultList = new ArrayList<>();
        for (UserMeal meal : meals) {
            boolean time = TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime);
            boolean excess = temp.get(meal.getDateTime().toLocalDate()).intValue() > caloriesPerDay;
            if (time) {
                resultList.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess));
            }
        }

        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        Map<LocalDate, Integer> temp = meals.stream()
                .collect(Collectors.groupingBy
                         (m -> m.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)
                        ));

        temp.entrySet().stream().forEach(System.out::println);


        List<UserMealWithExcess> resultList = new ArrayList<>();


         meals.stream()
            .filter(e -> TimeUtil.isBetweenHalfOpen(e.getDateTime().toLocalTime(), startTime, endTime))
             .collect(Collectors.toList());

         return null;


    }
}
