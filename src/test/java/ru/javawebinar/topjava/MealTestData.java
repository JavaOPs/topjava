package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDateTime;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 3;
    public static final int NOT_FOUND = START_SEQ + 10;
    public static final LocalDate localDate = parseLocalDate("2020-01-30");

    public static final Meal meal_1 = new Meal(MEAL_ID, parseLocalDateTime("2020-01-30 10:00:00"), "Завтрак", 500);
    public static final Meal meal_2 = new Meal(START_SEQ + 4, parseLocalDateTime("2020-01-30 13:00:00"), "Обед", 1000);
    public static final Meal meal_3 = new Meal(START_SEQ + 5, parseLocalDateTime("2020-01-30 20:00:00"), "Ужин", 500);
    public static final Meal meal_4 = new Meal(START_SEQ + 6, parseLocalDateTime("2020-01-31 00:00:00"), "Еда на граничное значение", 100);
    public static final Meal meal_5 = new Meal(START_SEQ + 7, parseLocalDateTime("2020-01-31 10:00:00"), "Завтрак", 1000);
    public static final Meal meal_6 = new Meal(START_SEQ + 8, parseLocalDateTime("2020-01-31 13:00:00"), "Обед", 500);
    public static final Meal meal_7 = new Meal(START_SEQ + 9, parseLocalDateTime("2020-01-31 20:00:00"), "Ужин", 410);

    public static Meal getNew() {
        return new Meal(null, parseLocalDateTime("2021-01-30 10:00:00"), "Завтрак", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal_1);
        updated.setDateTime(parseLocalDateTime("2021-01-30 10:00:00"));
        updated.setDescription("New_Description");
        updated.setCalories(330);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}