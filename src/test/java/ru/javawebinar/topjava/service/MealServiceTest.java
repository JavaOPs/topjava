package ru.javawebinar.topjava.service;

import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class MealServiceTest {
    private static final Logger log = getLogger("result");

    private static StringBuilder results;

//    @Rule
    public ExpectedException thrown;

//    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch;

    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }

//    @AfterClass
    public static void printResult() {
//        log.info("\n---------------------------------" +
//                "\nTest                 Duration, ms" +
//                "\n---------------------------------" +
//                results +
//                "\n---------------------------------");
    }

//    @Autowired
    private MealService service;

//    @Test
    public void testDelete() throws Exception {
//        service.delete(MEAL1_ID, USER_ID);
//        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

//    @Test
    public void testDeleteNotFound() throws Exception {
//        thrown.expect(NotFoundException.class);
//        service.delete(MEAL1_ID, 1);
    }

//    @Test
    public void testSave() throws Exception {
//        Meal created = getCreated();
//        service.create(created, USER_ID);
//        assertMatch(service.getAll(USER_ID), created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

//    @Test
    public void testGet() throws Exception {
//        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
//        assertMatch(actual, ADMIN_MEAL1);
    }

//    @Test
    public void testGetNotFound() throws Exception {
//        thrown.expect(NotFoundException.class);
//        service.get(MEAL1_ID, ADMIN_ID);
    }

//    @Test
    public void testUpdate() throws Exception {
//        Meal updated = getUpdated();
//        service.update(updated, USER_ID);
//        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

//    @Test
    public void testUpdateNotFound() throws Exception {
//        thrown.expect(NotFoundException.class);
//        thrown.expectMessage("Not found entity with id=" + MEAL1_ID);
//        service.update(MEAL1, ADMIN_ID);
    }

//    @Test
    public void testGetAll() throws Exception {
//        assertMatch(service.getAll(USER_ID), MEALS);
    }

//    @Test
    public void testGetBetween() throws Exception {
//        assertMatch(service.getBetweenDates(
//                LocalDate.of(2015, Month.MAY, 30),
//                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }
}