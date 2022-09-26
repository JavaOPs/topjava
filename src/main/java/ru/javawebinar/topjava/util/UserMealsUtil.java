package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                // order of new objects is specially mixed
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000)
        );

        System.out.println(filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(filteredByStreamsOptional(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Objects.requireNonNull(meals, "Meals must not be null");

        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();
        meals.forEach(meal -> caloriesPerDayMap.merge(
                meal.getDateTime().toLocalDate(),
                meal.getCalories(),
                Integer::sum));

        List<UserMealWithExcess> resultList = new ArrayList<>();
        meals.forEach(meal -> {
            if (isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                resultList.add(new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesPerDayMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));

            }
        });

        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Objects.requireNonNull(meals, "Meals must not be null");

        Map<LocalDate, Integer> caloriesPerDayMap = meals.stream()
                .collect(Collectors.toMap(
                        meal -> meal.getDateTime().toLocalDate(),
                        UserMeal::getCalories,
                        Integer::sum)
                );

        return meals.stream()
                .filter(meal -> isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesPerDayMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByStreamsOptional(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Objects.requireNonNull(meals, "Meals must not be null");

        return meals.stream()
                .collect(
                        () -> new HashMap<LocalDate, Pair<Integer, List<UserMeal>>>(),
                        (list, meal) -> {
                            Pair<Integer, List<UserMeal>> pairCaloriesMeals = list.computeIfAbsent(
                                    meal.getDateTime().toLocalDate(),
                                    unused -> new Pair<>(0, new ArrayList<>()));

                            int caloriesOldValue = pairCaloriesMeals.getArgA();
                            List<UserMeal> mealList = pairCaloriesMeals.getArgB();
                            int caloriesNewValue = caloriesOldValue + meal.getCalories();

                            pairCaloriesMeals.setArgA(caloriesNewValue);
                            if (isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                                mealList.add(meal);
                            }
                        },
                        HashMap::putAll)
                .values().stream().flatMap(caloriesMealsList ->
                        caloriesMealsList.getArgB().stream()
                                .map(meal -> new UserMealWithExcess(
                                        meal.getDateTime(),
                                        meal.getDescription(),
                                        meal.getCalories(),
                                        caloriesMealsList.getArgA() > caloriesPerDay)))
                .collect(Collectors.toList());
    }
}