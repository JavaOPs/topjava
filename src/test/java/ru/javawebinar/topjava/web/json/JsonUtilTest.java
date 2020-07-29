package ru.javawebinar.topjava.web.json;

import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

class JsonUtilTest {

    @Test
    void readWriteValue() throws Exception {
        String json = JsonUtil.writeValue(ADMIN_MEAL1);
        System.out.println(json);
        Meal meal = JsonUtil.readValue(json, Meal.class);
        MEAL_MATCHER.assertMatch(meal, ADMIN_MEAL1);
    }

    @Test
    void readWriteValues() throws Exception {
        String json = JsonUtil.writeValue(MEALS);
        System.out.println(json);
        List<Meal> meals = JsonUtil.readValues(json, Meal.class);
        MEAL_MATCHER.assertMatch(meals, MEALS);
    }
}