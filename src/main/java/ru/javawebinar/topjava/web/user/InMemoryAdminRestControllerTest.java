package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

class InMemoryAdminRestControllerTest {
    private static final Logger log = LoggerFactory.getLogger(InMemoryAdminRestControllerTest.class);

    private static ConfigurableApplicationContext appCtx;
    private static AdminRestController controller;

    @BeforeAll
    static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/inmemory.xml");
        log.info("\n{}\n", Arrays.toString(appCtx.getBeanDefinitionNames()));
        controller = appCtx.getBean(AdminRestController.class);
    }

    @AfterAll
    static void afterClass() {
//        May cause during JUnit "Cache is not alive (STATUS_SHUTDOWN)" as JUnit share Spring context for speed
//        http://stackoverflow.com/questions/16281802/ehcache-shutdown-causing-an-exception-while-running-test-suite
//        appCtx.close();
    }

    @BeforeEach
    void setUp() throws Exception {
        // re-initialize
        InMemoryUserRepository repository = appCtx.getBean(InMemoryUserRepository.class);
        repository.init();
    }

    @Test
    void delete() throws Exception {
        controller.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> controller.get(USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> controller.delete(10));
    }
}