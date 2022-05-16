package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
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

        List<UserMealWithExcess> mealsTo1 = filteredByCycleOp2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo1.forEach(System.out::println);

        System.out.println(filteredByStreamOp2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        if (Objects.isNull(meals) || Objects.isNull(startTime) || Objects.isNull(endTime) || caloriesPerDay < 0) return new ArrayList<>();
        List<UserMealWithExcess> result = new ArrayList<>();
        Map<LocalDate, Integer> caloriesPerDate = new HashMap<>();
        for (UserMeal userMeal : meals) {
            caloriesPerDate.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum);
        }
        for (UserMeal userMeal : meals) {
            LocalDateTime mealDate = userMeal.getDateTime();
            if (TimeUtil.isBetweenHalfOpen(mealDate.toLocalTime(), startTime, endTime) && caloriesPerDate.get(mealDate.toLocalDate()) > caloriesPerDay)
                result.add(new UserMealWithExcess(mealDate, userMeal.getDescription(), userMeal.getCalories(), true));
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        if (Objects.isNull(meals) || Objects.isNull(startTime) || Objects.isNull(endTime) || caloriesPerDay < 0) return new ArrayList<>();
        Map<LocalDate, Integer> caloriesPerDate = meals.stream()
                .collect(Collectors.toMap(userMeal -> userMeal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));
        return meals.stream().filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime) &&
                        caloriesPerDate.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)
                .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByCycleOp2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        if (Objects.isNull(meals) || Objects.isNull(startTime) || Objects.isNull(endTime) || caloriesPerDay < 0) return new ArrayList<>();
        List<UserMealWithExcess> result = new LinkedList<>();
        Map<LocalDate, Integer> caloriesPerDate = new HashMap<>();
        LinkedList<UserMeal> userMeals = new LinkedList<>();
        for (UserMeal userMeal : meals) {
            caloriesPerDate.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum);
            userMeals.add(userMeal);
        }
        while (userMeals.size() > 0) {
            UserMeal userMeal = userMeals.poll();
            if (caloriesPerDate.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay &&
                    TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                result.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true));
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreamOp2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay){
        if (Objects.isNull(meals) || Objects.isNull(startTime) || Objects.isNull(endTime) || caloriesPerDay < 0) return new ArrayList<>();
        Collector<UserMeal, Map.Entry<LinkedList<UserMeal>, Map<LocalDate, Integer>>, List<UserMealWithExcess>> userMealCollector = Collector.of(
                () -> new AbstractMap.SimpleEntry<>(new LinkedList<>(), new HashMap<>()),
                (entry, userMeal) -> {
                    entry.getKey().add(userMeal);
                    entry.getValue().merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum);
                },
                (entry1, entry2) -> {
                    entry1.getKey().addAll(entry2.getKey());
                    for (Map.Entry<LocalDate, Integer> me : entry2.getValue().entrySet())
                        entry1.getValue().merge(me.getKey(), me.getValue(), Integer::sum);
                    return entry1;
                },
                entry -> {
                    List<UserMealWithExcess> result = new LinkedList<>();
                    while (entry.getKey().size() > 0) {
                        UserMeal userMeal = entry.getKey().poll();
                        if (entry.getValue().get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay &&
                                TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                            result.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true));
                    }
                    return result;
                }
        );
        return meals.stream().collect(userMealCollector);
    }
}
