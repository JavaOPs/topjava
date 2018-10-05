package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
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
        List<UserMealWithExceed> userMealWithExceeds =
                getFilteredWithExceededByStream(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        userMealWithExceeds.stream().forEach(System.out::println);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> resultList = new ArrayList<>();
        List<UserMeal> filteredList = new ArrayList<>();
        Map<LocalDate, Integer> sumsOfCalories = new TreeMap<>();

        for (UserMeal meal : mealList) {
            LocalDateTime dateTime = meal.getDateTime();

            Integer curSum = sumsOfCalories.getOrDefault(dateTime.toLocalDate(), 0);
            sumsOfCalories.put(dateTime.toLocalDate(), meal.getCalories() + curSum);

            if (endTime.isAfter(dateTime.toLocalTime())
                    && startTime.isBefore(dateTime.toLocalTime())) {
                filteredList.add(meal);
            }
        }

        for (UserMeal meal : mealList) {
            LocalDate localDate  = meal.getDateTime().toLocalDate();
            int sumForDay = sumsOfCalories.get(localDate);
            resultList.add(new UserMealWithExceed(meal, sumForDay > caloriesPerDay));
        }

        return resultList;
    }




    public static List<UserMealWithExceed>  getFilteredWithExceededByStream(List<UserMeal> mealList,
                                                                            LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMeal> filteredMeals =
                mealList.stream()
                        .filter(meal ->
                                endTime.isAfter(meal.getDateTime().toLocalTime()))
                        .filter(meal ->
                                startTime.isBefore(meal.getDateTime().toLocalTime()))
                        .collect(Collectors.toList());

        Map<LocalDate, Integer> sumOfCalloriesByDay = filteredMeals.stream()
                    .collect(Collectors.groupingBy(m -> m.getDateTime().toLocalDate(),
                            Collectors.summingInt(UserMeal::getCalories)));

        return filteredMeals.stream()
                .map(m -> new UserMealWithExceed(m, sumOfCalloriesByDay.get(m.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());

    }

}
