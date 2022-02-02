package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMealWithExcess {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int boolResult = excess ? 1 : 0;

        return prime * ((dateTime == null ? 0 : dateTime.hashCode())
                + (description == null ? 0 : description.hashCode())
                    +(calories)
                        + boolResult);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        UserMealWithExcess mealWithExcess = (UserMealWithExcess) obj;

        return this.dateTime.equals(mealWithExcess.dateTime)
                && (mealWithExcess.description != null && this.description.equals(mealWithExcess.description))
                && (this.calories == mealWithExcess.calories)
                && (mealWithExcess.excess == this.excess);
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}
