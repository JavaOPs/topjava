package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by Dmitriy_Varygin on 19.03.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest extends TestCase {

    @Autowired
    UserMealService service;

    @Autowired
    DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        UserMeal userMeal = service.get(ID1, USER_ID);
        MATCHER.assertEquals(MEAL1, userMeal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(ID1, 1);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(ID1, USER_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL2), service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(ID1, 1);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        final Collection<UserMeal> betweenDates = service.getBetweenDates(LocalDate.of(2011, 1, 1),
                LocalDate.of(2015, 11, 25), ADMIN_ID);
        MATCHER.assertCollectionEquals(betweenDates, BETWEEN_OF_ADMIN_ID);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        final Collection<UserMeal> betweenDateTimes = service.getBetweenDateTimes(LocalDateTime.of(2011, 1, 1, 10, 0, 0),
                LocalDateTime.of(2015, 11, 25, 15, 0, 0), ADMIN_ID);
        MATCHER.assertCollectionEquals(betweenDateTimes, BETWEEN_OF_ADMIN_ID);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<UserMeal> all = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(all, ALL_OF_USER_ID);
    }

    @Test
    public void testUpdate() throws Exception {
        service.update(MEAL4_UPDATED, ADMIN_ID);
        MATCHER.assertEquals(MEAL4_UPDATED, service.get(MEAL4_UPDATED.getId(), ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        service.update(MEAL4_UPDATED, 1);
    }

    @Test
    public void testSave() throws Exception {
        final UserMeal created = service.save(MEAL7_FOR_SAVE, ADMIN_ID);
        assertEquals((int) created.getId(), ID7);
        MATCHER.assertCollectionEquals(service.getAll(ADMIN_ID), ALL_OF_ADMIN_ID_AFTER_SAVE_MEAL7);
    }
}