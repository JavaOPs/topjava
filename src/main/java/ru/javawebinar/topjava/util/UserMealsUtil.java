package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;


import java.time.LocalDate;
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
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 2000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 500)
        );
        System.out.println(getFilteredMealsWithExceed(mealList, LocalTime.of(7, 0, 0), LocalTime.of(12, 0, 0), 2000).get(1).isExceed());


    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceed(List<UserMeal> mealList, LocalTime timeFrom, LocalTime timeUntil, int caloriesPerDay) {
        List<UserMealWithExceed> mealWithExceedList;
        mealWithExceedList = mealList.stream()
                .map(x -> new UserMealWithExceed(x.getDateTime(), x.getDescription(), x.getCalories(),
                        mealList.stream()
                                .filter(y -> y.getDateTime().toLocalDate().equals(x.getDateTime().toLocalDate()))
                                .mapToInt(UserMeal::getCalories).sum() > caloriesPerDay)).collect(Collectors.toList());
        return mealWithExceedList.stream()
                .filter(x -> x.getDateTime().toLocalTime().isAfter(timeFrom) && x.getDateTime().toLocalTime().isBefore(timeUntil))
                .collect(Collectors.toList());
    }
}
