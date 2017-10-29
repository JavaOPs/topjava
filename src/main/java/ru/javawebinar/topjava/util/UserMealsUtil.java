package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
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
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
    }


    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        //create map to find calories for each day.
        Map<LocalDate, Integer> caloriesMap = new HashMap<>();
        //summarize daily calories. From here we can find out which day has excess of calories.
        for (UserMeal userMeal : mealList){
            caloriesMap.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum);
        }
        //create return-list.
        List<UserMealWithExceed> listOfUserMealWithExceed = new ArrayList<>();
        for (UserMeal userMeal : mealList){
            //if date and time satisfies condition..
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)){
                //create UserMealWithExceed and add it to return-list
                listOfUserMealWithExceed.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), caloriesMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return listOfUserMealWithExceed;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded_Optional(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesMap;
        caloriesMap = mealList.stream()
                .collect(
                        Collectors.toMap(u -> u.getDateTime().toLocalDate(), u -> u.getCalories(), Integer::sum)
                );
        List<UserMealWithExceed> listOfUserMealWithExceed = mealList.stream()
                .filter(u -> TimeUtil.isBetween(u.getDateTime().toLocalTime(), startTime, endTime))
                .map(u -> new UserMealWithExceed(u.getDateTime(), u.getDescription(), u.getCalories(), caloriesMap.get(u.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());

        return listOfUserMealWithExceed;
    }
}
