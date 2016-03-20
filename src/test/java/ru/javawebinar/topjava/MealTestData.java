package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static final int ID1 = START_SEQ + 2;
    public static final int ID2 = START_SEQ + 3;

    public static final int ID3 = START_SEQ + 4;
    public static final int ID4 = START_SEQ + 5;
    public static final int ID5 = START_SEQ + 6;
    public static final int ID6 = START_SEQ + 7;

    public static final UserMeal MEAL1 = new UserMeal(ID1, LocalDateTime.of(2015, 5, 5, 4, 5, 6), "Завтрак", 600);
    public static final UserMeal MEAL2 = new UserMeal(ID2, LocalDateTime.of(2010, 5, 31, 22, 0, 0), "Ужин", 500);

    public static final UserMeal MEAL3 = new UserMeal(ID3, LocalDateTime.of(2008, 10, 25, 10, 0, 0), "Завтрак", 600);
    public static final UserMeal MEAL4 = new UserMeal(ID4, LocalDateTime.of(2015, 11, 25, 15, 0, 0), "Обед", 800);
    public static final UserMeal MEAL5 = new UserMeal(ID5, LocalDateTime.of(2011, 1, 1, 14, 0, 0), "Плотный обед", 1600);
    public static final UserMeal MEAL6 = new UserMeal(ID6, LocalDateTime.of(2011, 1, 1, 10, 0, 0), "Завтрак", 500);



    public static final List<UserMeal> ALL_OF_ID_1 = Arrays.asList(MEAL1, MEAL2);
    public static final List<UserMeal> BETWEEN_OF_ID_2 = Arrays.asList(MEAL4, MEAL5, MEAL6);

}
