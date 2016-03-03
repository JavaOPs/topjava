package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL_WITH_EXCEED_STRING_MODEL_MATCHER;
import static ru.javawebinar.topjava.MealTestData.USER_MEALS;
import static ru.javawebinar.topjava.MealTestData.USER_MEALS_WITH_EXCEED;
import static ru.javawebinar.topjava.web.meal.UserMealRestController.REST_URL;

/**
 * Created by boklag on 01.03.16.
 */
public class UserMealRestControllerTest extends AbstractControllerTest{


    @Test
    public void testGet() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(USER_MEAL_WITH_EXCEED_STRING_MODEL_MATCHER.contentListMatcher(USER_MEALS_WITH_EXCEED)));
    }


}
