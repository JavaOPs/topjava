package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

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

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final int _minOfDayStartTime    = startTime.getHour() * 60 + startTime.getMinute();
        final int _minOfDayEndTime      = endTime.getHour() * 60 + endTime.getMinute();
        List<UserMealWithExcess> _retUserMealExcess = new ArrayList<UserMealWithExcess>();
        Map<LocalDate, Integer> _caloriesOfDay = new HashMap<LocalDate, Integer>();

        for(UserMeal um: meals){
            if( ( (um.getDateTime().getHour() * 60 + um.getDateTime().getMinute()) >= _minOfDayStartTime ) &&
                    ((um.getDateTime().getHour() * 60 + um.getDateTime().getMinute()) <= _minOfDayEndTime) )
                _retUserMealExcess.add( new UserMealWithExcess(um.getDateTime(), um.getDescription(), um.getCalories(), false ) );

            LocalDate _dt = LocalDate.of(um.getDateTime().getYear(), um.getDateTime().getMonth(), um.getDateTime().getDayOfMonth());
            if(_caloriesOfDay.containsKey(_dt)) _caloriesOfDay.put(_dt, _caloriesOfDay.get(_dt).intValue() + um.getCalories());
            else _caloriesOfDay.put(_dt, um.getCalories());
        };

        _retUserMealExcess.forEach( ume -> {
            _caloriesOfDay.forEach((dt, val) -> {
                if((dt.getYear() == ume.getDateTime().getYear()) &&
                        (dt.getMonth() == ume.getDateTime().getMonth()) &&
                        (dt.getDayOfMonth() == ume.getDateTime().getDayOfMonth()) &&
                        val > caloriesPerDay) {

                    ume.setExcess(true);
                }
            });
        });


        return _retUserMealExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
