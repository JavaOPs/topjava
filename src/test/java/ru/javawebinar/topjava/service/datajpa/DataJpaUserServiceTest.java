package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.BaseUserServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.meals;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends BaseUserServiceTest {
    @Test
    public void getByIdWithMeals() {
        User user = super.service.getUserWithMeals(USER_ID);
        MEAL_MATCHER.assertMatch(user.getMeals(), meals);
    }
}
