package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();

        filteredWithExceeded.forEach(System.out::println);
    }

    private static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream().collect(Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
        .map(um -> new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(),
                caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
        .collect(Collectors.toList());
    }



    public static List<UserMealWithExceed> getFilteredWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        List<UserMealWithExceed> list = new ArrayList<>();
        Map<LocalDate, List<UserMeal>> map = new HashMap<>();

        for (UserMeal meal : mealList) {
            if (meal.getDateTime().toLocalTime().isAfter(startTime) && meal.getDateTime().toLocalTime().isBefore(endTime)) {
                if (map.containsKey(meal.getDateTime().toLocalDate())) {
                    map.get(meal.getDateTime().toLocalDate()).add(meal);
                } else {
                    ArrayList<UserMeal> newList = new ArrayList<>();
                    newList.add(meal);
                    map.put(meal.getDateTime().toLocalDate(), newList);
                }
            }
        }

        for (Map.Entry<LocalDate, List<UserMeal>> entry : map.entrySet()) {
            int sumDay = 0;
            List<UserMeal> listDay = new ArrayList<>();

            for (int i = 0; i < entry.getValue().size(); i++) {
                sumDay += entry.getValue().get(i).getCalories();
                listDay.add(entry.getValue().get(i));

                if (++i == entry.getValue().size()) {

                    for (UserMeal userMeal : listDay) {
                        list.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), sumDay > caloriesPerDay));
                    }
                    listDay = new ArrayList<>();
                    sumDay = 0;
                }
            }
        }
        return list;
    }
}
