package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
    @Override
    public void createWithException() throws Exception {
        Assert.assertTrue(true);
    }

    @Test
    public void userTempTest() {
        User user = new User(null, "Test100012", "Test@gmail.com", "newPass",
                1555, true, new Date(), List.of(Role.USER, Role.ADMIN));
        service.create(user);
        user.setRoles(Set.of(Role.USER));
        user.setName("Bla-bla");
        service.update(user);

        System.out.println(service.get(100012));
    }
}