package ru.javawebinar.topjava.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@ToString
@EqualsAndHashCode
public class MealWithExcess {

    private final Integer id;
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final AtomicBoolean excess;

    public MealWithExcess() {
        this(null, LocalDateTime.now(), "", 0, new AtomicBoolean(false));
    }

    public MealWithExcess(Integer id, LocalDateTime dateTime, String description, int calories, AtomicBoolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }
}
