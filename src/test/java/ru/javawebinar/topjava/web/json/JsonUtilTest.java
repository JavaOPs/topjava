package ru.javawebinar.topjava.web.json;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

class JsonUtilTest {
    private static final Logger log = LoggerFactory.getLogger(JsonUtilTest.class);

    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(adminMeal1);
        log.info(json);
        Meal meal = JsonUtil.readValue(json, Meal.class);
        MEAL_MATCHER.assertMatch(meal, adminMeal1);
    }

    @Test
    void readWriteValues() {
        String json = JsonUtil.writeValue(meals);
        log.info(json);
        List<Meal> actual = JsonUtil.readValues(json, Meal.class);
        MEAL_MATCHER.assertMatch(actual, meals);
    }
}