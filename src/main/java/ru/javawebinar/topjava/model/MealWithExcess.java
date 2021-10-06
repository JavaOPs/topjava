package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealWithExcess extends Meal{
    private boolean excess;

    public MealWithExcess(LocalDateTime dateTime, String description, int calories, boolean excess) {
        super(dateTime, description, calories);
        this.excess = excess;
    }
}
