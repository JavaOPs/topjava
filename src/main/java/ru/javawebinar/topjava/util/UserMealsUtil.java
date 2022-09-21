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
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        // Люди ищут решение домашки на staskoverflow ))
        // https://ru.stackoverflow.com/questions/1174993/Как-сделать-суммирование-вводимых-в-hashmap-значений-java

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

            if (isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                resultList.add(new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        new BooleanValue(false)));

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
                        new BooleanValue(caloriesPerDayMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay)))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByCyclesOptional(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> resultList = new ArrayList<>();
        if (meals == null) {
            return resultList;
        }


        /*
        Как вариант я вижу обернуть в UserMealsWithExcess переменную excess и сделать ее объектом.
        В цикле собирать выходной список и параллельно вести еще мапу с ключом - датой.
        А для значения мапы использовать объект - пару значений.
        Чтобы хранить в нем сумму калорий на этот день и ссылку на объект excess. Как только поймали превышение,
        сразу опа и присвоили excess.setValue(true). И сразу у всех в выходном списке все присвоилось.
        */
        Map<LocalDate, Pair<Integer, BooleanValue>> caloriesPerDayAndExcessMap = new HashMap<>();

        meals.forEach(meal -> {
            Pair<Integer, BooleanValue> pairCaloriesAndExcess = caloriesPerDayAndExcessMap.computeIfAbsent(
                    meal.getDateTime().toLocalDate(),
                    unused -> new Pair<>(0, new BooleanValue(false)));

            int caloriesOldValue = pairCaloriesAndExcess.getArgA();
            int caloriesNewValue = caloriesOldValue + meal.getCalories();
            boolean isCaloriesExcess = caloriesNewValue > caloriesPerDay;

            pairCaloriesAndExcess.setArgA(caloriesNewValue);

            BooleanValue caloriesExcessValue = pairCaloriesAndExcess.getArgB();
            if (caloriesOldValue <= caloriesPerDay && isCaloriesExcess) {
                caloriesExcessValue.setValue(true);
            }

            if (isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                resultList.add(new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesExcessValue));
            }
        });

        return resultList;
    }
}