package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000));
        System.out.println("======");
        System.out.println(getFilteredWithExceededByStream(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000));
    }

    /** Method return filtered list with correctly exceeded field*/
    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> userMealWithExceedsList = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            LocalTime mealLocalTime = userMeal.getDateTime().toLocalTime();
            if (TimeUtil.isBetween(mealLocalTime, startTime, endTime)){
                UserMealWithExceed userMealWithExceed = new UserMealWithExceed(userMeal, userMeal.getCalories() > caloriesPerDay);
                userMealWithExceedsList.add(userMealWithExceed);
            }
        }
        return userMealWithExceedsList;
    }

    /** Method return filtered list with correctly exceeded field with streams*/
    public static List<UserMealWithExceed>  getFilteredWithExceededByStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return mealList.stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExceed(userMeal, userMeal.getCalories() > caloriesPerDay))
                .collect(Collectors.toList());
    }
}