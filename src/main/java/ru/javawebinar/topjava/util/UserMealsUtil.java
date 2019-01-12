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
                getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        userMealWithExceeds.stream().forEach(System.out::println);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumsOfCalories = new HashMap<>();

        for (UserMeal meal : mealList) {
            LocalDateTime dateTime = meal.getDateTime();

            Integer curSum = sumsOfCalories.getOrDefault(dateTime.toLocalDate(), 0);
            sumsOfCalories.put(dateTime.toLocalDate(), meal.getCalories() + curSum);

        }

        List<UserMealWithExceed> resultList = new ArrayList<>();
        for (UserMeal meal : mealList) {
            LocalDateTime dateTime = meal.getDateTime();
            LocalDate localDate  = dateTime.toLocalDate();

            if (!TimeUtil.isBetween(dateTime.toLocalTime() , startTime, endTime)) {
                continue;
            }
            int sumForDay = sumsOfCalories.get(localDate);
            UserMealWithExceed userMealWithExceed =
                    new UserMealWithExceed(dateTime, meal.getDescription(), meal.getCalories(), sumForDay > caloriesPerDay);
            resultList.add(userMealWithExceed);
        }

        return resultList;
    }




    public static List<UserMealWithExceed>  getFilteredWithExceededByStream(List<UserMeal> mealList,
                                                                            LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMeal> filteredMeals =
                mealList.stream()
                        .filter(meal ->TimeUtil.isBetween(meal.getDateTime().toLocalTime() , startTime, endTime))
                        .collect(Collectors.toList());

        Map<LocalDate, Integer> sumOfCalloriesByDay = mealList.stream()
                    .collect(Collectors.groupingBy(m -> m.getDateTime().toLocalDate(),
                            Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(meal ->TimeUtil.isBetween(meal.getDateTime().toLocalTime() , startTime, endTime))
                .map(m ->
                        new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(),
                                sumOfCalloriesByDay.get(m.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());

    }

}
