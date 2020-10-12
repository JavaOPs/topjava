package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> mealsUser1 = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.OCTOBER, 11, 10, 0), "Завтрак Us1", 500),
            new Meal(LocalDateTime.of(2020, Month.OCTOBER, 11, 13, 0), "Обед Us1", 1000),
            new Meal(LocalDateTime.of(2020, Month.OCTOBER, 11, 20, 0), "Ужин Us1", 500),
            new Meal(LocalDateTime.of(2020, Month.OCTOBER, 12, 0, 0), "Еда на граничное значение Us1", 100),
            new Meal(LocalDateTime.of(2020, Month.OCTOBER, 12, 10, 0), "Завтрак Us1", 1000),
            new Meal(LocalDateTime.of(2020, Month.OCTOBER, 12, 13, 0), "Обед Us1", 500),
            new Meal(LocalDateTime.of(2020, Month.OCTOBER, 12, 20, 0), "Ужин Us1", 410)
    );

    public static final List<Meal> mealsUser2 = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.OCTOBER, 11, 10, 0), "Завтрак Us2", 500),
            new Meal(LocalDateTime.of(2020, Month.OCTOBER, 11, 13, 0), "Обед Us2", 1000),
            new Meal(LocalDateTime.of(2020, Month.OCTOBER, 11, 20, 0), "Ужин Us2", 500),
            new Meal(LocalDateTime.of(2020, Month.OCTOBER, 12, 0, 0), "Еда на граничное значение Us2", 100),
            new Meal(LocalDateTime.of(2020, Month.OCTOBER, 12, 10, 0), "Завтрак Us2", 1000),
            new Meal(LocalDateTime.of(2020, Month.OCTOBER, 12, 13, 0), "Обед Us2", 500),
            new Meal(LocalDateTime.of(2020, Month.OCTOBER, 12, 20, 0), "Ужин Us2", 410)
    );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return filterByPredicate(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return filterByPredicate(meals, caloriesPerDay, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
    }

    public static List<MealTo> getFilteredByDateTimeTos(Collection<Meal> meals, int caloriesPerDay, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return getFilteredTos(meals, caloriesPerDay, startDateTime.toLocalTime(), endDateTime.toLocalTime())
                .stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime))
                .collect(Collectors.toList()
                );
    }

    public static List<MealTo> filterByPredicate(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
