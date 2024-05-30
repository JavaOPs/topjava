package ru.javawebinar.topjava.service.jdbc;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.BaseUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends BaseUserServiceTest {
}
