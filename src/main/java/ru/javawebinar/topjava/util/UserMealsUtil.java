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
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> mealWithExcesses = new ArrayList<>();

        // find all dates in list and add them into hashmap
        HashMap<LocalDate, List<UserMeal>> hashMap = new HashMap<LocalDate, List<UserMeal>>();
        for (UserMeal userMeal:meals) {
            if (!hashMap.containsKey(userMeal.getDateTime().toLocalDate())) {
                List<UserMeal> list = new ArrayList<UserMeal>();
                list.add(userMeal);
                hashMap.put(userMeal.getDateTime().toLocalDate(), list);
            } else {
                hashMap.get(userMeal.getDateTime().toLocalDate()).add(userMeal);
            }
        }

        //iterate through hashmap
        for (Map.Entry<LocalDate, List<UserMeal>> entry : hashMap.entrySet()) {
//            System.out.println(entry.getKey() + "/" + entry.getValue());
            int sumOfCaloriesPerDay=0;
            boolean excess = false;
            //calculate sumOfCaloriesPerDay
            for (UserMeal userMeal:entry.getValue()) {
                sumOfCaloriesPerDay = sumOfCaloriesPerDay + userMeal.getCalories();
            }
            if (caloriesPerDay<sumOfCaloriesPerDay)
                excess = true;
            //add values to mealWithExcesses
            for (UserMeal userMeal:entry.getValue()) {
                boolean isBeforeStartTime = userMeal.getDateTime().toLocalTime().isBefore(startTime);
                boolean isBeforeEndTime = userMeal.getDateTime().toLocalTime().isBefore(endTime);
                if (!isBeforeStartTime&isBeforeEndTime){
                    mealWithExcesses.add(new UserMealWithExcess(userMeal.getDateTime(),userMeal.getDescription(),userMeal.getCalories(),excess));
                }
//                mealWithExcesses.add(new UserMealWithExcess(userMeal.getDateTime(),userMeal.getDescription(),userMeal.getCalories(),excess));
            }
        }
        return mealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
