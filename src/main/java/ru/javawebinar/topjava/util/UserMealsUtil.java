package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed>mealExceedList = getFilteredWithExceeded2(mealList, LocalTime.of(0, 0), LocalTime.of(13,0), 2000);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        mealExceedList.forEach(meal-> System.out.println(
                String.format("%s %s %d %b",
                meal.getDateTime().format(formatter),
                meal.getDescription(),
                meal.getCalories(),
                meal.isExceed())));
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer>caloriesByDate = mealList
                .stream()
                .collect(Collectors
                        .groupingBy(UserMeal::getDate,
                                Collectors.summingInt(UserMeal::getCalories)
                        )
                );

        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
//                .filter(meal -> caloriesByDate.get(meal.getDate())>caloriesPerDay)
//                .filter(meal -> meal.getTime().isAfter(startTime))
//                .filter(meal -> meal.getTime().isBefore(endTime))
                .map(meal->new UserMealWithExceed(meal, (caloriesByDate.get(meal.getDate())>caloriesPerDay)))
                .collect(Collectors.toList())
        ;
    }
}
