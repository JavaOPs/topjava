package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMeal extends UserMealBase{

    public UserMeal(final LocalDateTime dateTime, final String description, final int calories) {
        super(dateTime, description, calories);
    }
}
