package ru.javawebinar.topjava.web.user;

import org.junit.Test;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * GKislin
 * 10.04.2015.
 */
public class RootControllerTest extends AbstractControllerTest {

    @Test
    public void testUserList() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
//                .andExpect(model().attribute("userList", hasSize(2)))
//                .andExpect(model().attribute("userList", hasItem(
//                        allOf(
//                                hasProperty("id", is(START_SEQ)),
//                                hasProperty("name", is(USER.getName()))
//                        )
//                )));
    }
}