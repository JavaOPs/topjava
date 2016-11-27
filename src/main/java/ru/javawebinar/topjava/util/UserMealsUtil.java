package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        List<UserMealWithExceed> retVal = new ArrayList<UserMealWithExceed>();
        List<UserMeal> tmpList = new ArrayList<UserMeal>();

        int counter = 0;
        LocalDate tmpDate = mealList.get(0).getDateTime().toLocalDate();

        for (UserMeal m : mealList) {

            if(tmpDate.equals(m.getDateTime().toLocalDate())) {
                counter += m.getCalories();
                System.out.println(tmpDate+" "+m.getDescription());
                if(counter > caloriesPerDay) {
                    System.out.println(counter + " more than 2000");
                }
            } else {
                tmpDate = m.getDateTime().toLocalDate();
                counter = m.getCalories();
            }


            if(m.getDateTime().toLocalTime().isAfter(startTime) && m.getDateTime().toLocalTime().isBefore(endTime)){

                tmpList.add(m);
            }

            // System.out.println(m.getDateTime() + " " + m.getCalories() + " " + m.getDescription());

        }

        for(UserMeal u : tmpList) {
             System.out.println(u.getDateTime() + " " + u.getCalories() + " " + u.getDescription());
        }

        return null;
    }
}
