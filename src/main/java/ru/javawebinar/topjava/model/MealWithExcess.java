package ru.javawebinar.topjava.model;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@ToString
public class MealWithExcess {

    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final AtomicBoolean excess;

    public MealWithExcess() {
        this.dateTime = LocalDateTime.now();
        this.description = "";
        this.calories = 0;
        this.excess = new AtomicBoolean(false);
    }

    public MealWithExcess(LocalDateTime dateTime, String description, int calories, AtomicBoolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }
}
