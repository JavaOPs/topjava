package ru.javawebinar.topjava.model.loop;

import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.util.List;

public class UserMealWithExcessGroupByDay {
    private final LocalDate date;

    private int sumOfCaloriesPerDay;

    private final boolean excess;

    private final List<UserMealWithExcess> userMealWithExcessList;

    public UserMealWithExcessGroupByDay(LocalDate date, int sumOfCaloriesPerDay, boolean excess, List<UserMealWithExcess> userMealWithExcessList) {
        this.date = date;
        this.sumOfCaloriesPerDay = sumOfCaloriesPerDay;
        this.excess = excess;
        this.userMealWithExcessList = userMealWithExcessList;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getSumOfCaloriesPerDay() {
        return sumOfCaloriesPerDay;
    }

    public boolean isExcess() {
        return excess;
    }

    public List<UserMealWithExcess> getUserMealWithExcessList() {
        return userMealWithExcessList;
    }

    public UserMealWithExcessGroupByDay merge(UserMealWithExcessGroupByDay another) {
        this.sumOfCaloriesPerDay += another.sumOfCaloriesPerDay;
        return this;
    }
}
