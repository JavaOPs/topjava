package ru.javawebinar.topjava.model.stream;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.ArrayList;
import java.util.List;

public class UserMealWithExcessStream {

    private int sumOfCaloriesPerDay;

    private final List<UserMeal> userMealList;

    private final UserMeal userMeal;

    public UserMealWithExcessStream(int sumOfCaloriesPerDay, UserMeal userMeal) {
        this.sumOfCaloriesPerDay = sumOfCaloriesPerDay;
        this.userMeal = userMeal;
        this.userMealList = new ArrayList<>();
        this.userMealList.add(userMeal);
    }

    public UserMealWithExcessStream merge(UserMealWithExcessStream another) {
        this.sumOfCaloriesPerDay += another.sumOfCaloriesPerDay;
        this.userMealList.add(another.userMeal);
        return this;
    }

    public static UserMealWithExcessStream fromUserMeal(UserMeal userMeal) {
        return new UserMealWithExcessStream(userMeal.getCalories(), userMeal);
    }

    public List<UserMeal> getUserMealList() {
        return userMealList;
    }

    public int getSumOfCaloriesPerDay() {
        return sumOfCaloriesPerDay;
    }
}
