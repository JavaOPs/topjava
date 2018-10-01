package ru.javawebinar.topjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Meal {
    private LocalDateTime dateTime;
    private String description;
    private int calories;
}
