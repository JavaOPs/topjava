package ru.javawebinar.topjava.service.jdbc;

import org.junit.Ignore;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
@Ignore
public class JdbcMealServiceTest extends AbstractMealServiceTest {
}