package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

        List<UserMealWithExceed> userMealWithExceeds = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        //List<UserMealWithExceed> userMealWithExceeds = getFilteredWithExceededOptional(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        for (UserMealWithExceed userMealWithExceed : userMealWithExceeds) {
            System.out.println(userMealWithExceed.getDescription() + " " + userMealWithExceed.getDateTime().toLocalTime() + " " + userMealWithExceed.isExceed());
        }

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();

        Map<LocalDate, Integer> localDateExceedMap = new HashMap<LocalDate, Integer>();

        for (UserMeal userMeal : mealList) {
            if (localDateExceedMap.containsKey(userMeal.getDate())) {
                localDateExceedMap.put(userMeal.getDate(), localDateExceedMap.get(userMeal.getDate()) + userMeal.getCalories());
            } else {
                localDateExceedMap.put(userMeal.getDate(), userMeal.getCalories());
            }
        }
        for (UserMeal userMeal : mealList) {
            if (userMeal.getTime().isAfter(startTime) && userMeal.getTime().isBefore(endTime)) {
                userMealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), localDateExceedMap.get(userMeal.getDate()) > caloriesPerDay));
            }
        }
        return userMealWithExceeds;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededOptional(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream()
                .collect(
                        Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories))
                );

        return mealList.stream()
                .filter(meal -> isDateBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(toList());
    }

    public static UserMealWithExceed createWithExceed(UserMeal meal, boolean exceeded) {
        return new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }

    public static  boolean isDateBetween(LocalTime time, LocalTime startTime, LocalTime endTime) {
        return time.isAfter(startTime) && time.isBefore(endTime);
    }
}
