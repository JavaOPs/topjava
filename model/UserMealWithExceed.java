package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMealWithExceed extends UserMeatsUtil {
    private final LocalDateTime dateTime;

    private final String description;

    final int calories;

    int caloriesPerDay;

    private final boolean exceed;

    if(caloriesPerDay > calories)

    {
        exceed = false;

        else
        {
            exceed = true;
        }
    }

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }
}
