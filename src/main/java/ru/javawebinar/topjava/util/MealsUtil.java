package ru.javawebinar.topjava.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@UtilityClass
public class MealsUtil {

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static List<MealTo> getWithExcesses(Collection<Meal> meals, int caloriesPerDay) {
        return getFiltered(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public static List<MealTo> getFiltered(Collection<Meal> meals, @Nullable LocalTime startTime, @Nullable LocalTime endTime, int caloriesPerDay) {
        return meals.stream().collect(Collectors.groupingBy(Meal::getDate)).values().stream()
                .flatMap(dayMeals -> {
                    AtomicBoolean excess = new AtomicBoolean(dayMeals.stream().mapToInt(Meal::getCalories).sum() > caloriesPerDay);
                    return dayMeals.stream().filter(meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime))
                            .map(meal -> createWithExcess(meal, excess));
                }).collect(Collectors.toList());
    }

    private static MealTo createWithExcess(Meal meal, AtomicBoolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
