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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
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

    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {

    }

    @Test
    public void testGetAll() throws Exception {
        Collection<UserMeal> all = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(all, ALL_OF_ID_1);
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testSave() throws Exception {

    }
}