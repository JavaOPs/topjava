package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@SpringJUnitConfig(locations = {"classpath:spring/spring-app.xml", "classpath:spring/inmemory.xml"})
class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @BeforeEach
    void setUp() throws Exception {
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
