package ru.javawebinar.topjava.model;

import lombok.ToString;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

@ToString
public class MealWithExcess {

    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final AtomicBoolean excess;

    public MealWithExcess(LocalDateTime dateTime, String description, int calories, AtomicBoolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }
}
