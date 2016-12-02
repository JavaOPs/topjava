package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 20, 0), "Ужин", 510)
        );

         getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                   LocalTime endTime, int caloriesPerDay) {
        TimeUtil timeUtil = new TimeUtil();

        //Проеобразование List к многократно используемому стриму
        UserMeal[] myArray = (UserMeal[]) mealList.toArray();
        Supplier<Stream<UserMeal>> mealStream = () -> Arrays.stream(myArray);

        //подсчет калорий с группировкой по дню
        Map<LocalDate, Integer> mealByCloriesSum2 = mealStream.get().collect(
                Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));

        //List для отфильтрованых данных
        List<UserMeal> mealList1 = new ArrayList<>();
        mealList.stream()
                .filter(meal -> timeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .sorted(Comparator.comparing(UserMeal::getDate))
                .forEach(i -> mealList1.add(new UserMeal(i.getDateTime(), i.getDescription(), i.getCalories())));
        //вывод отфильтрованых данных
        //mealList1.forEach((u)-> System.out.println(u.getDateTime()+" "+ u.getDescription()+" "+u.getCalories()));

        //заполнение объектами класса UserMealWithExceed с установкой флага превышения калорий
        int calor = 0;
        boolean exceed;
        ArrayList<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        for (UserMeal ml : mealList1) {
            calor = mealByCloriesSum2.get(ml.getDate());
            exceed = calor > caloriesPerDay;
            userMealWithExceedList.add(new UserMealWithExceed(ml.getDateTime(), ml.getDescription(),
                                                              ml.getCalories(), exceed));
        }

        //итоговый вывод массива объектов
        userMealWithExceedList.forEach((u) -> System.out.println(u.getDateTime() + " " + u.getDescription() +
                " " + u.getCalories() + " " + u.getExceeded()));

        // TODO return filtered list with correctly exceeded field
        return userMealWithExceedList;
    }
}
