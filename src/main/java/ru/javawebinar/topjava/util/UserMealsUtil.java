package ru.javawebinar.topjava.util;

import com.sun.javafx.collections.MappingChange;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 1500);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
//        Map map = new MappingChange.Map<>();
//        map.merge()

        int caloriesTemp = 0;
        List<UserMealWithExcess> mealsTo = new ArrayList<>();
        List<UserMeal> filteredMeals = new ArrayList<>();
        for (UserMeal currentMeal : meals){
            LocalDateTime currentMealDateTime = currentMeal.getDateTime();
            LocalTime currentMealTime = currentMealDateTime.toLocalTime();
            if (TimeUtil.isBetweenHalfOpen(currentMealTime, startTime, endTime)){
                filteredMeals.add(currentMeal);
                caloriesTemp += currentMeal.getCalories();
            }
        }

        boolean excess = caloriesTemp > caloriesPerDay;

        for (UserMeal currentMeal : filteredMeals){
            LocalDateTime currentMealDateTime = currentMeal.getDateTime();
            mealsTo.add(new UserMealWithExcess(currentMealDateTime, currentMeal.getDescription(), currentMeal.getCalories(), excess));
        }

        return mealsTo;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        int caloriesConsumed = meals
                .stream()
                .filter(s -> TimeUtil.isBetweenHalfOpen(s.getDateTime().toLocalTime(), startTime, endTime))
                .map(fx->fx.getCalories())
                .reduce(0, (acc, fx) -> acc + fx);

        boolean excess = caloriesConsumed > caloriesPerDay;

        List<UserMealWithExcess> mealsTo = meals
                .stream()
                .filter(s -> TimeUtil.isBetweenHalfOpen(s.getDateTime().toLocalTime(), startTime, endTime))
                .map(x -> new UserMealWithExcess(x.getDateTime(), x.getDescription(), x.getCalories(), excess))
                .collect(Collectors.toList());

        return mealsTo;
    }
}
