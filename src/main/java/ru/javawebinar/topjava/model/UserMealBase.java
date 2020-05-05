package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

abstract public class UserMealBase {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public UserMealBase(final LocalDateTime dateTime, final String description, final int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
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
}
