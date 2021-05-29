package ru.javawebinar.topjava.model.loop;

import java.time.LocalDate;
import java.util.List;

public class UserMealWithExcessGroupByDayOptional2 {
    private final LocalDate date;

    private final int sumOfCaloriesPerDay;

    private final boolean excess;

    private final List<Integer> indexes;

    public UserMealWithExcessGroupByDayOptional2(LocalDate date, int sumOfCaloriesPerDay, boolean excess, List<Integer> indexes) {
        this.date = date;
        this.sumOfCaloriesPerDay = sumOfCaloriesPerDay;
        this.excess = excess;
        this.indexes = indexes;
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

    public List<Integer> getIndexes() {
        return indexes;
    }
}
