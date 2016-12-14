package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class MealsUtil {

    public final static ConcurrentMap<Integer, Meal> meals = Arrays.stream(new Object[][]{
            {1, new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500)},
            {2, new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000)},
            {3, new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500)},
            {4, new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000)},
            {5, new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500)},
            {6, new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)},
            {7, new Meal(7, LocalDateTime.of(2015, Month.MAY, 31, 22, 0), "Поздний жин", 300)},
    }).collect(Collectors.toConcurrentMap(kv->(Integer) kv[0],kv -> (Meal) kv[1]));

    /*
    public static void main(String[] args) {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<MealWithExceed> mealsWithExceeded = getFilteredWithExceeded(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsWithExceeded.forEach(System.out::println);

        System.out.println(getFilteredWithExceededByCycle(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }
*/
    public static List<MealWithExceed> getFilteredWithExceeded(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealWithExceed> getAllWithExceeded(ConcurrentMap<Integer, Meal> meals, int caloriesPerDay) {

        List<Meal> result = meals.entrySet().stream()
                .map(x -> x.getValue())
                .collect(Collectors.toList());

        Map<LocalDate, Integer> caloriesSumByDate = result.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return result.stream()
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealWithExceed> getFilteredWithExceededByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealWithExceed> mealsWithExceeded = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsWithExceeded.add(createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealsWithExceeded;
    }

    public static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }

    public static void deleteMeal(int id) {
        if(meals.containsKey(id))
            meals.remove(id);
    }
}