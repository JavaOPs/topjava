package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMealWithExcess extends UserMealBase{

    private final boolean excess;

    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, boolean excess) {
        super(dateTime, description, calories);
        this.excess = excess;
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{" +
                "dateTime=" + getDateTime() +
                ", description='" + getDescription() + '\'' +
                ", calories=" + getCalories() +
                ", excess=" + isExcess() +
                '}';
    }

    public boolean isExcess() {
        return excess;
    }
}
