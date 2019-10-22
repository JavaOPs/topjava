package ru.javawebinar.topjava.to;

import java.time.LocalDateTime;

public class MealTo {
    private final Integer id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    private final Integer userId;



    public MealTo(Integer userId, Integer id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.userId=userId;
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public MealTo() {
        userId = 0;
        id = 0;
        dateTime = LocalDateTime.now();
        description = "";
        calories = 0;
        excess = false;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

//    public LocalDateTime getDateTime() {
//        return dateTime;
//    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "userId="+userId+
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}