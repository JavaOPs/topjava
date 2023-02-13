package ru.javawebinar.topjava.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode (callSuper = true, onlyExplicitlyIncluded = true)
@ToString
public class Meal extends AbstractBaseEntity {

    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;

    public Meal() {
        this(null, LocalDateTime.now(), "", 0);
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDate getDate() {
        return this.dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return this.dateTime.toLocalTime();
    }
}
