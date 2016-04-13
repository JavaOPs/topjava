package ru.javawebinar.topjava.web;

import org.junit.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;
import java.util.Collection;

import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;

public class InMemoryAdminRestControllerTest {
    private static ConfigurableApplicationContext appCtx;
    private static AdminRestController controller;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-mvc.xml", "spring/mock.xml");
        System.out.println("\n" + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");
        controller = appCtx.getBean(AdminRestController.class);
    }

    @Before
    public void setUp() throws Exception {
        // Re-initialize
        UserRepository repository = appCtx.getBean(UserRepository.class);
        repository.getAll().forEach(u -> repository.delete(u.getId()));
        repository.save(USER);
        repository.save(ADMIN);
    }

    @AfterClass
    public static void afterClass() {
//        May cause during JUnit "Cache is not alive (STATUS_SHUTDOWN)" as JUnit share Spring context for speed
//        http://stackoverflow.com/questions/16281802/ehcache-shutdown-causing-an-exception-while-running-test-suite
//        appCtx.close();
    }

    @Test
    public void testDelete() throws Exception {
        controller.delete(UserTestData.USER_ID);
        Collection<User> users = controller.getAll();
        Assert.assertEquals(users.size(), 1);
        Assert.assertEquals(users.iterator().next(), ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        controller.delete(10);
    }
}