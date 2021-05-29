package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.*;
import ru.javawebinar.topjava.model.loop.UserMealWithExcessGroupByDay;
import ru.javawebinar.topjava.model.loop.UserMealWithExcessGroupByDayOptional2;
import ru.javawebinar.topjava.model.stream.UserMealWithExcessStream;
import ru.javawebinar.topjava.model.stream.UserMealWithExcessStreamOptional2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 9, 0), "Обед", 200),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 11, 0), "Ужин", 5000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 8, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 7, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        //List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        //List<UserMealWithExcess> mealsTo = filteredByCyclesOptional2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        //System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(filteredByStreamsOptional2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        //mealsTo.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> result = new ArrayList<>();
        Map<LocalDate, UserMealWithExcessGroupByDay> mealsGroupByDay = new HashMap<>();

        meals.forEach(meal -> {
            if (!TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                return;
            }

            LocalDate date = meal.getDateTime().toLocalDate();
            UserMealWithExcessGroupByDay mealGroupByDay = mealsGroupByDay.getOrDefault(date,
                    new UserMealWithExcessGroupByDay(date, 0, false, new ArrayList<>())
            );

            int sumCaloriesPerDay = meal.getCalories() + mealGroupByDay.getSumOfCaloriesPerDay();
            boolean excess = sumCaloriesPerDay > caloriesPerDay;
            List<UserMealWithExcess> userMealWithExcessList = mealGroupByDay.getUserMealWithExcessList();

            userMealWithExcessList.add(new UserMealWithExcess(
                    meal.getDateTime(),
                    meal.getDescription(),
                    meal.getCalories(),
                    false)
            );

            UserMealWithExcessGroupByDay newUserMeal = new UserMealWithExcessGroupByDay(
                    date,
                    sumCaloriesPerDay,
                    excess,
                    userMealWithExcessList
            );
            mealsGroupByDay.merge(date, newUserMeal, (oldValue, newValue) -> newUserMeal);
        });

        // Максимальная сложность 3N, с учетом прохода по всему списку выше
        mealsGroupByDay.forEach((index, mealGroupByDay) ->
            mealGroupByDay.getUserMealWithExcessList().forEach(userMealWithExcess ->
                result.add(new UserMealWithExcess(
                    userMealWithExcess.getDateTime(),
                    userMealWithExcess.getDescription(),
                    userMealWithExcess.getCalories(),
                    mealGroupByDay.isExcess()
                ))
            )
        );

        return result;
    }

    public static List<UserMealWithExcess> filteredByCyclesOptional2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> result = new ArrayList<>();
        Map<LocalDate, UserMealWithExcessGroupByDayOptional2> mealsGroupByDay = new HashMap<>();

        // Проход в один цикл, допускается проход по подсписку. Максимальная сложность 2N
        meals.forEach(meal -> {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                LocalDate date = meal.getDateTime().toLocalDate();
                UserMealWithExcessGroupByDayOptional2 mealGroupByDay = mealsGroupByDay.getOrDefault(date,
                        new UserMealWithExcessGroupByDayOptional2(date, 0, false, new ArrayList<>())
                );

                int sumCaloriesPerDay = meal.getCalories() + mealGroupByDay.getSumOfCaloriesPerDay();
                boolean excess = sumCaloriesPerDay > caloriesPerDay;
                List<Integer> indexes = mealGroupByDay.getIndexes();

                UserMealWithExcess userMealWithExcess = new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        excess
                );
                result.add(userMealWithExcess);
                indexes.add(result.indexOf(userMealWithExcess));

                if (excess) {
                    mealGroupByDay.getIndexes().forEach(index -> {
                        UserMealWithExcess resultMeal = result.get(index);
                        UserMealWithExcess newUserMeal = new UserMealWithExcess(resultMeal.getDateTime(), resultMeal.getDescription(), resultMeal.getCalories(), excess);
                        result.set(index, newUserMeal);
                    });
                }

                UserMealWithExcessGroupByDayOptional2 newUserMeal = new UserMealWithExcessGroupByDayOptional2(
                        date,
                        sumCaloriesPerDay,
                        excess,
                        indexes);
                mealsGroupByDay.merge(date, newUserMeal, (oldValue, newValue) -> newUserMeal);
            }
        });

        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // max O(N) = 3N
        return meals.stream().filter(meal ->
                TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)
        ).collect(Collectors.toMap(
            userMeal -> Collections.singletonList(userMeal.getDateTime().toLocalDate()),
            UserMealWithExcessStream::fromUserMeal,
            UserMealWithExcessStream::merge
        )).values().stream().flatMap(entry ->
            entry.getUserMealList().stream().map(userMeal -> new UserMealWithExcess(
                userMeal.getDateTime(),
                userMeal.getDescription(),
                userMeal.getCalories(),
            entry.getSumOfCaloriesPerDay() > caloriesPerDay
        ))).collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByStreamsOptional2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // meals.stream().collect получаем группировку по дате -> UserMeal. В группировку попадают только подходящие по дате.
        // Далее выводим все получившиеся значения с подменой excess.
        // Получилось сделать только с двойным проходом.
        return meals.stream().collect(Collectors.toMap(
                userMeal -> Collections.singletonList(userMeal.getDateTime().toLocalDate()),
                UserMealWithExcessStreamOptional2::fromUserMeal,
                (oldValue, newValue) -> oldValue.merge(newValue, startTime, endTime)
        )).values().stream().flatMap(entry ->
                entry.getUserMealList().stream().map(userMeal -> new UserMealWithExcess(
                        userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        entry.getSumOfCaloriesPerDay() > caloriesPerDay
                ))).collect(Collectors.toList());
    }
}
