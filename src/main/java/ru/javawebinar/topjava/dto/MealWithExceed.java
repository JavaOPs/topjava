package ru.javawebinar.topjava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MealWithExceed {
    private LocalDateTime dateTime;
    private String description;
    private int calories;
    private boolean exceed;
}