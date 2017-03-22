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
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceededOpt1(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        System.out.println();
        getFilteredWithExceededOpt2(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceededOpt1(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> sumMap = new HashMap<>();
        for (UserMeal meal : mealList) {
            LocalDate date = meal.getDateTime().toLocalDate();
            sumMap.merge(date, meal.getCalories(), Integer::sum);
        }

        return prepareFinalList(mealList, startTime, endTime, caloriesPerDay, sumMap);
    }

    public static List<UserMealWithExceed> getFilteredWithExceededOpt2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> sumMap = mealList.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map((meal) -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), sumMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .peek(System.out::println)
                .collect(Collectors.toList());

    }

    private static List<UserMealWithExceed> prepareFinalList(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay, Map<LocalDate, Integer> sumMap){
        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal meal : mealList) {
            LocalTime time = meal.getDateTime().toLocalTime();
            LocalDate date = meal.getDateTime().toLocalDate();
            if (TimeUtil.isBetween(time, startTime, endTime)) {
                UserMealWithExceed newUserMeal = new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), sumMap.get(date) > caloriesPerDay);
                result.add(newUserMeal);
                System.out.println(newUserMeal);
            }
        }
        return result;
    }
}
