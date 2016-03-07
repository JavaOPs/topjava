package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMealWithExceed {
    protected final long id;
    private final String formattedDateTime;
    protected final LocalDateTime dateTime;
    protected final String description;
    protected final int calories;
    protected final boolean exceed;

    public UserMealWithExceed(long id, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
        formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", Locale.ENGLISH));
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
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

    public long getId() {
        return id;
    }

    public boolean isExceed() {
        return exceed;
    }

    public String getFormattedDateTime() {
        return formattedDateTime;
    }
}
