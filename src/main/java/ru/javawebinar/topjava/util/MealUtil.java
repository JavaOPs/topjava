package ru.javawebinar.topjava.util;


import ru.javawebinar.topjava.dto.MealWithExceed;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.dao.MealDao.mealList;

public class MealUtil {

    private MealUtil() {}

    public static void main(String[] args) {
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<MealWithExceed> getFilteredWithExceeded(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        return null;
    }
}