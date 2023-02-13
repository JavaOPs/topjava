package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class MealsUtil {

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final Collection<Meal> MEALS = Arrays.asList(
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public static List<MealTo> getWithExcesses(Collection<Meal> meals, int caloriesPerDay) {
        return getFiltered(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public static List<MealTo> getFiltered(Collection<Meal> meals, @Nullable LocalTime startTime, @Nullable LocalTime endTime, int caloriesPerDay) {
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
