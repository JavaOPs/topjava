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
        System.out.println(filteredByCyclesOptional(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(filteredByStreamsOptional(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        // Люди ищут решение домашки на staskoverflow ))
        // https://ru.stackoverflow.com/questions/1174993/Как-сделать-суммирование-вводимых-в-hashmap-значений-java
        // Жалко, что я наткнулся на эту ссылку уже после того, как сам разобрался. Хотя наверно так даже лучше.

        List<UserMealWithExcess> resultList = new ArrayList<>();
        if (meals == null) {
            return resultList;
        }

        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();
        meals.forEach(meal -> {
            caloriesPerDayMap.merge(
                    meal.getDateTime().toLocalDate(),
                    meal.getCalories(),
                    Integer::sum);

            // такой вариант решения потребовал добавить сеттер для поля excess в UserMealWithExcess
            // можно реализовать используя только конструктор, но тогда это сравнение надо будет вынести отсюда
            // и придется повторно проходить по всему meals, а не только по отобранному списку resultList
            if (isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                resultList.add(new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        false));

            }
        });

        resultList.forEach(meal -> meal.setExcess(caloriesPerDayMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));

        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        if (meals == null) {
            return new ArrayList<>();
        }

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

    public static List<UserMealWithExcess> filteredByCyclesOptional(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> resultList = new ArrayList<>();
        if (meals == null) {
            return resultList;
        }

        Map<LocalDate, Pair<Integer, List<UserMealWithExcess>>> mealsPerDayMap = new HashMap<>();

        meals.forEach(meal -> {
        });

        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreamsOptional(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        if (meals == null) {
            return new ArrayList<>();
        }

        return meals.stream()
                .collect(
                        () -> new HashMap<LocalDate, Pair<Integer, List<UserMealWithExcess>>>(),
                        (list, meal) -> {
                            Pair<Integer, List<UserMealWithExcess>> pairCaloriesMeals = list.computeIfAbsent(
                                    meal.getDateTime().toLocalDate(),
                                    unused -> new Pair<>(0, new ArrayList<>()));

                            int caloriesOldValue = pairCaloriesMeals.getArgA();
                            int caloriesNewValue = caloriesOldValue + meal.getCalories();
                            boolean isCaloriesExcessed = caloriesNewValue > caloriesPerDay;

                            pairCaloriesMeals.setArgA(caloriesNewValue);

                            List<UserMealWithExcess> mealWithExcessList = pairCaloriesMeals.getArgB();
                            if (isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                                mealWithExcessList.add(new UserMealWithExcess(
                                        meal.getDateTime(),
                                        meal.getDescription(),
                                        meal.getCalories(),
                                        isCaloriesExcessed));
                            }

                            if (caloriesOldValue <= caloriesPerDay && isCaloriesExcessed) {
                                mealWithExcessList.forEach(m -> m.setExcess(true));
                            }
                        },
                        HashMap::putAll
                ).values().stream().flatMap(pair -> pair.getArgB().stream()).collect(Collectors.toList());
    }

    private void processMeals(Map<LocalDate, Pair<Integer, List<UserMealWithExcess>>> mealsPerDayMap,
                              List<UserMeal> meals, UserMeal meal, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Pair<Integer, List<UserMealWithExcess>> pairCaloriesMeals = mealsPerDayMap.computeIfAbsent(
                meal.getDateTime().toLocalDate(),
                unused -> new Pair<>(0, new ArrayList<>()));

        int caloriesOldValue = pairCaloriesMeals.getArgA();
        int caloriesNewValue = caloriesOldValue + meal.getCalories();
        boolean isCaloriesExcessed = caloriesNewValue > caloriesPerDay;

        pairCaloriesMeals.setArgA(caloriesNewValue);

        List<UserMealWithExcess> mealWithExcessList = pairCaloriesMeals.getArgB();
        if (isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
            mealWithExcessList.add(new UserMealWithExcess(
                    meal.getDateTime(),
                    meal.getDescription(),
                    meal.getCalories(),
                    isCaloriesExcessed));
        }

        if (caloriesOldValue <= caloriesPerDay && isCaloriesExcessed) {
            mealWithExcessList.forEach(m -> m.setExcess(true));
        }
    }
}