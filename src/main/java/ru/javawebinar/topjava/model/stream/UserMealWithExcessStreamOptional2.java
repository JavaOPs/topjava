package ru.javawebinar.topjava.model.stream;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UserMealWithExcessStreamOptional2 {

    private int sumOfCaloriesPerDay;

    private final List<UserMeal> userMealList;

    private final UserMeal userMeal;

    public UserMealWithExcessStreamOptional2(int sumOfCaloriesPerDay, UserMeal userMeal) {
        this.sumOfCaloriesPerDay = sumOfCaloriesPerDay;
        this.userMeal = userMeal;
        this.userMealList = new ArrayList<>();
        this.userMealList.add(userMeal);
    }

    public UserMealWithExcessStreamOptional2 merge(UserMealWithExcessStreamOptional2 another, LocalTime startTime, LocalTime endTime) {
        if (TimeUtil.isBetweenHalfOpen(another.userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
            this.sumOfCaloriesPerDay += another.sumOfCaloriesPerDay;
            this.userMealList.add(another.userMeal);
        }
        return this;
    }

    public static UserMealWithExcessStreamOptional2 fromUserMeal(UserMeal userMeal) {
        return new UserMealWithExcessStreamOptional2(userMeal.getCalories(), userMeal);
    }

    public List<UserMeal> getUserMealList() {
        return userMealList;
    }

    public int getSumOfCaloriesPerDay() {
        return sumOfCaloriesPerDay;
    }
}
