package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

      /*  List<UserMealWithExceed> exceededMeals = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        for (UserMealWithExceed userMealWithExceed : exceededMeals){
            System.out.println(userMealWithExceed.getDateTime() + " "+
            userMealWithExceed.getDescription() + " " +
            userMealWithExceed.getCalories() + " "+
            userMealWithExceed.isExceed());
        }*/
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

//        в этот список будем писать результаты
        List<UserMealWithExceed> result = new ArrayList<>();

//        предполагаем что mealList приходит неотсортированный и сортируем его по DateTime
        mealList.sort(new Comparator<UserMeal>() {
            @Override
            public int compare(UserMeal o1, UserMeal o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });

//          текущий день
        int currentDay = 0;

//        количество калорий за этот день
        int currentCalories = 0;

        for (UserMeal currentUserMeal : mealList){

//            формируем DateTime из параметра метода startTime
            LocalDateTime startPeriod = LocalDateTime.of(
                    currentUserMeal.getDateTime().getYear(),
                    currentUserMeal.getDateTime().getMonthValue(),
                    currentUserMeal.getDateTime().getDayOfMonth(),
                    startTime.getHour(),
                    startTime.getMinute(),
                    startTime.getSecond()
            );

//            формируем DateTime из параметра метода endTime
            LocalDateTime endPeriod = LocalDateTime.of(
                    currentUserMeal.getDateTime().getYear(),
                    currentUserMeal.getDateTime().getMonthValue(),
                    currentUserMeal.getDateTime().getDayOfMonth(),
                    endTime.getHour(),
                    endTime.getMinute(),
                    endTime.getSecond()
            );

//            если currentUserMeal не попадает в промежуток
//            переходим к следующему шагу
            if (currentUserMeal.getDateTime().isBefore(startPeriod)||
                    (currentUserMeal.getDateTime().isAfter(endPeriod))){
                continue;
            }

//            если currentUserMeal попадает в промежуток то:
//
//            1. Смотрим актуален ли еще currentDay и соответственно
//            рассчитанный currentCalories для него
            if (currentDay != currentUserMeal.getDateTime().getDayOfYear()){

//                2. если currentDay уже не актуален то рассчитываем заново его
                currentDay = currentUserMeal.getDateTime().getDayOfYear();

//                3. Прописываем ему начало
                LocalDateTime startDay = LocalDateTime.of(
                        currentUserMeal.getDateTime().getYear(),
                        currentUserMeal.getDateTime().getMonthValue(),
                        currentUserMeal.getDateTime().getDayOfMonth(),
                        0,
                        0,
                        0
                );
//                4. И конец
                LocalDateTime endDay = LocalDateTime.of(
                        currentUserMeal.getDateTime().getYear(),
                        currentUserMeal.getDateTime().getMonthValue(),
                        currentUserMeal.getDateTime().getDayOfMonth(),
                        23,
                        59,
                        59
                );

//                 5. И считаем currentCalories для нового currentDay

                IntSummaryStatistics sum = mealList.stream().filter((x)-> (x.getDateTime().isAfter(startDay) &&
                        x.getDateTime().isBefore(endDay))).
                        collect(Collectors.summarizingInt(p -> p.getCalories()));
                currentCalories = (int)sum.getSum();
            }

//             теперь формируем объект UserMealWithExceed
//             и добавляем его в список результатов
            result.add(new UserMealWithExceed(currentUserMeal.getDateTime(),
                    currentUserMeal.getDescription(),
                    currentUserMeal.getCalories(),
                    currentCalories > caloriesPerDay));

        }

//        всё
        return result;
    }
}
