package ru.javawebinar.topjava.web.meal;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController extends AbstractMealController {
/*
    public List<MealTo> getBetweenMeals(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                    @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        return getBetween(startDate, startTime, endDate, endTime);
    }*/
}