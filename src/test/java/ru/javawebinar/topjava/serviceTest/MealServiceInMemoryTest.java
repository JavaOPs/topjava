package ru.javawebinar.topjava.serviceTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.TestData.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mockTest.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.TestData.MealTestData.*;
import static ru.javawebinar.topjava.TestData.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.TestData.UserTestData.USER_ID;

@ContextConfiguration({"classpath:/spring/spring-app.xml", "classpath:/spring/mock.xml"})
@RunWith(SpringRunner.class)
public class MealServiceInMemoryTest {
    @Autowired
    private MealService service;

    @Autowired
    private InMemoryMealRepositoryImpl repository;

    @Before
    public void setUp() {
        repository.init();
    }

    @Test
    public void get() {
        Meal meal = service.get(2, USER_ID);
        MealTestData.assertMatch(meal, MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void getWithWrongUserId() {
        service.get(2, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(1, USER_ID);
        MealTestData.assertMatch(service.getAll(USER_ID),
                MEAL6, MEAL5, MEAL4, MEAL3, MEAL2
        );
    }

    @Test(expected = NotFoundException.class)
    public void deleteWithWrongUserId() {
        service.delete(1, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 31), LocalDate.of(2015, Month.MAY, 31), USER_ID);
        MealTestData.assertMatch(meals, MEAL6, MEAL5, MEAL4);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> meals = service.getBetweenDateTimes(
                LocalDateTime.of(2015, Month.MAY, 31, 10, 0),
                LocalDateTime.of(2015, Month.MAY, 31, 13, 0),
                USER_ID);
        MealTestData.assertMatch(meals, MEAL5, MEAL4);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(ADMIN_ID);
        MealTestData.assertMatch(meals, MEAL8, MEAL7);
    }


    @Test
    public void update() {
        Meal meal = service.get(3, USER_ID);
        meal.setCalories(9999);
        meal.setDescription("9999");
        meal.setDateTime(LocalDateTime.of(2999, Month.MAY, 9, 9, 9));
        service.update(meal, USER_ID);
        MealTestData.assertMatch(service.get(3, USER_ID), meal);
    }

    @Test(expected = NotFoundException.class)
    public void updateWithWrongId() {
        Meal meal = service.get(5, USER_ID);
        meal.setCalories(9999);
        meal.setDescription("9999");
        meal.setDateTime(LocalDateTime.of(2999, Month.MAY, 9, 9, 9));
        service.update(meal, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), "Полдник", 1000);
        Meal createdMeal = service.create(newMeal, USER_ID);
        newMeal.setId(createdMeal.getId());
        MealTestData.assertMatch(service.getAll(USER_ID), createdMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }
}