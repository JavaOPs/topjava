package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public static final UserMeal MEAL1 = new UserMeal(ID1, LocalDateTime.of(2015, 5, 5, 4, 5, 6), "Завтрак", 600);
    public static final UserMeal MEAL2 = new UserMeal(ID2, LocalDateTime.of(2010, 5, 31, 22, 0, 0), "Ужин", 500);

    public static final List<UserMeal> ALL_OF_ID_1;

    static {
        ALL_OF_ID_1 = new ArrayList<>();
        ALL_OF_ID_1.add(MEAL1);
        ALL_OF_ID_1.add(MEAL2);
        Collections.sort(ALL_OF_ID_1, (u1, u2) -> u2.getDateTime().compareTo(u1.getDateTime()));
    }

}
