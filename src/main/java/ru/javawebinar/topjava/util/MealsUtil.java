package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class MealsUtil {

    public static final List<Meal> MEAL_LIST = Arrays.asList(
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public static List<MealTo> getWithExcesses(List<Meal> mealList, int caloriesPerDay) {
        return getFilteredByStreams(mealList, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public static List<MealTo> getFilteredByCycles(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final Map<LocalDate, Integer> caloriesSumByDay = new HashMap<>();
        final Map<LocalDate, AtomicBoolean> excessesMap = new HashMap<>();

        final List<MealTo> mealsWithExcesses = new ArrayList<>();
        meals.forEach(meal -> {
            AtomicBoolean wrapBoolean = excessesMap.computeIfAbsent(meal.getDate(), date -> new AtomicBoolean());
            Integer dailyCalories = caloriesSumByDay.merge(meal.getDate(), meal.getCalories(), Integer::sum);
            if (dailyCalories > caloriesPerDay) {
                wrapBoolean.set(true);
            }
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsWithExcesses.add(createWithExcess(meal, wrapBoolean));
            }
        });
        return mealsWithExcesses;
    }

    public static List<MealTo> getFilteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream().collect(Collectors.groupingBy(Meal::getDate)).values().stream()
                .flatMap(dayMeals -> {
                    AtomicBoolean excess = new AtomicBoolean(dayMeals.stream().mapToInt(Meal::getCalories).sum() > caloriesPerDay);
                    return dayMeals.stream().filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                            .map(meal -> createWithExcess(meal, excess));
                }).collect(Collectors.toList());
    }

    private static MealTo createWithExcess(Meal meal, AtomicBoolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
