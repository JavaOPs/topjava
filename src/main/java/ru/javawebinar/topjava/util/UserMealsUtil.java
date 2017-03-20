package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
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
        List<UserMealWithExceed> result1 = getFilteredWithExceededOpt1(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 1900);
        List<UserMealWithExceed> result2 = getFilteredWithExceededOpt2(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 1900);
        List<UserMealWithExceed> result3 = getFilteredWithExceededOpt3(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 1900);
    }

    public static List<UserMealWithExceed> getFilteredWithExceededOpt1(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        Map<LocalDate, Integer> sumMap = new HashMap<>();
        for (UserMeal meal : mealList) {
            LocalDate date = meal.getDateTime().toLocalDate();
            sumMap.merge(date, meal.getCalories(), Integer::sum);
        }

        return prepareFinalList(mealList, startTime, endTime, caloriesPerDay, sumMap);
    }

    public static List<UserMealWithExceed> getFilteredWithExceededOpt2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        Map<LocalDate, Integer> sumMap = new HashMap<>();
        for (UserMeal meal : mealList) {
            LocalDate date = meal.getDateTime().toLocalDate();
            sumMap.put(date, sumMap.getOrDefault(date, 0) + meal.getCalories());
        }

        return prepareFinalList(mealList, startTime, endTime, caloriesPerDay, sumMap);
    }

    public static List<UserMealWithExceed> getFilteredWithExceededOpt3(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        Map<LocalDate, Integer> sumMap = mealList.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        Collector<UserMeal, ArrayList<UserMealWithExceed>, ArrayList<UserMealWithExceed>> mealWithExceedCollector =
                Collector.of(
                        ArrayList<UserMealWithExceed>::new,
                        (list, meal) -> list.add(new UserMealWithExceed(meal, sumMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay)),
                        (list, mealWithExceed) -> {list.addAll(mealWithExceed); return list;}
                );

        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(mealWithExceedCollector);

    }

    private static List<UserMealWithExceed> prepareFinalList(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay, Map<LocalDate, Integer> sumMap){
        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal meal : mealList) {
            LocalTime time = meal.getDateTime().toLocalTime();
            LocalDate date = meal.getDateTime().toLocalDate();
            if (TimeUtil.isBetween(time, startTime, endTime)) {
                result.add(new UserMealWithExceed(meal, sumMap.get(date) > caloriesPerDay));
            }
        }
        return result;
    }
}
