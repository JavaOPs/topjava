package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.MATCHER_WITH_EXCEED;
import static ru.javawebinar.topjava.MealTestData.USER_MEALS_WITH_EXCEED;
import static ru.javawebinar.topjava.web.meal.UserMealRestController.REST_URL;
import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by boklag on 01.03.16.
 */
public class UserMealRestControllerTest extends AbstractControllerTest {


    @Test
    public void testGet() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(USER_MEALS_WITH_EXCEED)));
    }

    @Test
    public void testFilter() throws Exception {
        mockMvc.perform(get(REST_URL + "filter?startDate=2015-05-30&startTime=07:00&endDate=2015-05-31&endTime=11:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(
                        UserMealsUtil.createWithExceed(MEAL4, true),
                        UserMealsUtil.createWithExceed(MEAL1, false)));
    }

    @Test
    public void testFilterAll() throws Exception {
        mockMvc.perform(get(REST_URL + "filter?startDate=&endTime="))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(
                        UserMealsUtil.getWithExceeded(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), LoggedUser.getCaloriesPerDay())));

    }
}