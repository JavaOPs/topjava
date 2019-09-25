package ru.javawebinar.topjava.util;

import org.junit.Test;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class UserMealsUtilTest {

    @Test
    public void testIsExceedWhenNoMeals() {
        List<UserMeal> meals = new ArrayList<>();
        boolean exceed = UserMealsUtil.isExceed(600, meals);
        assertFalse(exceed);
    }

    @Test
    public void testIsExceedWhenExceedsOneMeal() {
        List<UserMeal> meals = new ArrayList<>();
        meals.add(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Breakfast", 1000));
        boolean exceed = UserMealsUtil.isExceed(600, meals);
        assertTrue(exceed);
    }

    @Test
    public void testIsExceedWhenNotExceedsManyMeal() {
        List<UserMeal> meals = new ArrayList<>();
        meals.add(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Breakfast", 1000));
        meals.add(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Breakfast", 1000));
        meals.add(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Breakfast", 1000));
        boolean exceed = UserMealsUtil.isExceed(4000, meals);
        assertFalse(exceed);
    }
}
