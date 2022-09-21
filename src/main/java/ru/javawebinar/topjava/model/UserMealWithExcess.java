package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.BooleanValue;

import java.time.LocalDateTime;

public class UserMealWithExcess {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private BooleanValue excess;

    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, BooleanValue excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess.isTrue();
    }

    public void setExcess(boolean excess) {
        this.excess.setValue(excess);
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}