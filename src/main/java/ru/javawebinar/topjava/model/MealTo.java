package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealTo {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

//    private final Supplier<Boolean> excess;
//    private final AtomicBoolean excess;
    private final boolean excess;

    public MealTo(LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

//    public Boolean getExcess() {
//        return excess.get();
//    }

    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}