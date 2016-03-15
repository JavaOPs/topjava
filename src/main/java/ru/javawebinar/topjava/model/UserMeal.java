package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMeal {
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private Integer id;
    private Integer usersID;

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, null, dateTime, description, calories);
    }

    public UserMeal(Integer id, Integer userID, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.usersID = userID;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public UserMeal(Integer usersID, LocalDateTime dateTime, String description, int calories) {
        this(null, usersID, dateTime, description, calories);
    }

    public void setId(int id) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }

    public Integer getUserID() {
        return usersID;
    }


    public void setUserID(Integer usersID) {
        this.usersID = usersID;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                "usersID=" + usersID +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
