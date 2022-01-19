package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

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

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Set<UserMealWithExcess> resultSet = new HashSet<>();

        Map<LocalDate, Integer> temp = new HashMap<>();

        for (UserMeal m : meals) {
            if (TimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalTime(), startTime, endTime)) {

                LocalDate searchedDay = m.getDateTime().toLocalDate();

                if (!temp.containsKey(searchedDay)) temp.put(searchedDay, m.getCalories());

                else temp.put(searchedDay, temp.get(searchedDay) + m.getCalories());

                if (temp.get(searchedDay) > caloriesPerDay) // > or >=  ?
                    resultSet.add(new UserMealWithExcess(m.getDateTime(), "by circles", temp.get(searchedDay), true));
            }
        }

        return new ArrayList<>(resultSet);
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> temp = new HashMap<>();

        return meals.stream()
                .filter(m -> TimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalTime(), startTime, endTime))
                .map(m -> {

                    LocalDate searchedDay = m.getDateTime().toLocalDate();

                    if (!temp.containsKey(searchedDay)) temp.put(searchedDay, m.getCalories());

                    else temp.put(searchedDay, temp.get(searchedDay) + m.getCalories());

                    if (temp.get(searchedDay) > caloriesPerDay)
                        return new UserMealWithExcess(m.getDateTime(), "by stream", temp.get(searchedDay), true);
                    return null;
                })
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }
}


