package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

//delete
    public String getDescription(){
        return description;
    }
    public int getCalories(){
        return calories;
    }
    public LocalDateTime getDateTime(){
        return dateTime;
    }
    public boolean getExceeded(){
        return exceed;
    }
}
