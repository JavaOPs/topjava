package ru.javawebinar.topjava.service.datajpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    void getWithMeals() {
        User admin = service.getWithMeals(ADMIN_ID);
        USER_MATCHER.assertMatch(admin, UserTestData.admin);
        MEAL_MATCHER.assertMatch(admin.getMeals(), MealTestData.adminMeal2, MealTestData.adminMeal1);
    }

    @Test
    void getWithMealsNotFound() {
        Assertions.assertThrows(NotFoundException.class,
                () -> service.getWithMeals(NOT_FOUND));
    }
}