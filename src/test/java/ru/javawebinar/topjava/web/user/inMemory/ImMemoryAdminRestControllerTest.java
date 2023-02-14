package ru.javawebinar.topjava.web.user.inMemory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inMemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.javawebinar.topjava.TestData.*;

/**
 * @author Alexei Valchuk, 13.02.2023, email: a.valchukav@gmail.com
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/spring/spring-app.xml")
class ImMemoryAdminRestControllerTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @BeforeEach
    void setUp() {
        repository.init();
    }

    @Test
    void getAll() {
        List<User> all = controller.getAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(ADMIN));
        assertTrue(all.contains(USER));
    }

    @Test
    void get() {
        assertEquals(USER.getName(), controller.get(USER_ID).getName());
        assertEquals(ADMIN.getName(), controller.get(ADMIN_ID).getName());
    }

    @Test
    void create() {
        User newUser = new User(null, "DUMMY", "DUMMY", "DUMMY", Role.ROLE_USER);
        controller.create(newUser);
        assertEquals(3, newUser.getId());
        assertEquals("DUMMY", newUser.getName());
        assertEquals(3, repository.getAll().size());
    }

    @Test
    void delete() {
        controller.delete(USER_ID);
        controller.delete(ADMIN_ID);
        assertEquals(0, repository.getAll().size());
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotFoundException.class, () -> controller.delete(Integer.MAX_VALUE));
    }

    @Test
    void update() {
        String email = "DUMMY";
        ADMIN.setEmail(email);
        controller.update(ADMIN, ADMIN_ID);
        assertEquals(email, controller.get(ADMIN_ID).getEmail());
    }

    @Test
    void updateNotExist() {
        assertThrows(IllegalArgumentException.class, () -> controller.update(Mockito.mock(User.class), Integer.MAX_VALUE));
    }

    @Test
    void getByMail() {
        assertEquals(USER.getName(), controller.getByMail(USER.getEmail()).getName());
    }

    @Test
    void getByMailNotExist() {
        assertThrows(NotFoundException.class, () -> controller.getByMail("DUMMY"));
    }
}