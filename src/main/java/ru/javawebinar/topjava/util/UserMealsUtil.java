package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) throws Exception {
        Random random = new Random();

        List<UserMeal> mealList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            int month = random.nextInt(1) + 1;
            int day = random.nextInt(28) + 1;
            int hour = random.nextInt(23) + 1;
            int minute = random.nextInt(59) + 1;
            int calories = random.nextInt(4100) + 100;
            UserMeal userMeal = new UserMeal(LocalDateTime.of(2015, month, day, hour, minute), "Завтрак", calories);
            mealList.add(userMeal);
            //System.out.println(userMeal);
        }


        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList, LocalTime.of(0, 0), LocalTime.of(12, 0), 5000);
        for (UserMealWithExceed umwe : list) {
            System.out.println(umwe);
        }
        //org.openjdk.jmh.Main.main(args);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Collections.sort(mealList, (o1, o2) -> {
            if (o1.getDateTime().isEqual(o2.getDateTime())) {
                return 0;
            }
            return o1.getDateTime().isBefore(o2.getDateTime()) ? -1 : 1;
        });


        List<UserMealWithExceed> mealWithExceeds = new ArrayList<>();
        int caloriesCounter = 0;
        UserMeal userMealFirst = mealList.get(0);
        mealList.remove(0);
        UserMeal userMealPrevious = mealList.get(0);
        for (UserMeal userMeal : mealList) {
            if (userMeal.getDateTime().toLocalDate().isAfter(userMealPrevious.getDateTime().toLocalDate())) {
                caloriesCounter = 0;
            }
            caloriesCounter += userMeal.getCalories();
            LocalTime localTime = userMeal.getDateTime().toLocalTime();
            if (TimeUtil.isBetween(localTime,startTime,endTime)) {
                mealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), caloriesCounter > caloriesPerDay));
            }

            userMealPrevious = userMeal;
        }
        mealList.add(0, userMealFirst);
        return mealWithExceeds;
    }
}
