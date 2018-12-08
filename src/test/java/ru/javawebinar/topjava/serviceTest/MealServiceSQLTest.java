package ru.javawebinar.topjava.serviceTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.TestData.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.TestData.MealTestData.*;
import static ru.javawebinar.topjava.TestData.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.TestData.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceSQLTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(100003, USER_ID);
        //List<Meal> meals =service.getAll(USER_ID);
        MealTestData.assertMatch(meal, MEAL2_SQL);
    }

    @Test(expected = NotFoundException.class)
    public void getWithWrongUserId() {
        service.get(100002, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(100002, USER_ID);
        MealTestData.assertMatch(service.getAll(USER_ID),
                MEAL6_SQL, MEAL5_SQL, MEAL4_SQL, MEAL3_SQL, MEAL2_SQL
        );
    }

    @Test(expected = NotFoundException.class)
    public void deleteWithWrongUserId() {
        service.delete(100002, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 31), LocalDate.of(2015, Month.MAY, 31), USER_ID);
        MealTestData.assertMatch(meals, MEAL6_SQL, MEAL5_SQL, MEAL4_SQL);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> meals = service.getBetweenDateTimes(
                LocalDateTime.of(2015, Month.MAY, 31, 10, 0),
                LocalDateTime.of(2015, Month.MAY, 31, 13, 0),
                USER_ID);
        MealTestData.assertMatch(meals, MEAL5_SQL, MEAL4_SQL);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(ADMIN_ID);
        MealTestData.assertMatch(meals, MEAL8_SQL, MEAL7_SQL);
    }


    @Test
    public void update() {
        Meal meal = service.get(100004, USER_ID);
        meal.setCalories(9999);
        meal.setDescription("9999");
        meal.setDateTime(LocalDateTime.of(2999, Month.MAY, 9, 9, 9));
        service.update(meal, USER_ID);
        MealTestData.assertMatch(service.get(100004, USER_ID), meal);
    }

    @Test(expected = NotFoundException.class)
    public void updateWithWrongId() {
        Meal meal = service.get(100006, USER_ID);
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
        MealTestData.assertMatch(service.getAll(USER_ID), createdMeal, MEAL6_SQL, MEAL5_SQL, MEAL4_SQL, MEAL3_SQL, MEAL2_SQL, MEAL1_SQL);
    }
}
