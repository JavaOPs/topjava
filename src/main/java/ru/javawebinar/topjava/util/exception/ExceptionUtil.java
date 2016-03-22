package ru.javawebinar.topjava.util.exception;


/**
 * User: gkislin
 * Date: 14.05.2014
 */
public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static void check(boolean found, int id) {
        check(found, "id=" + id);
    }

    public static <T> T check(T object, int id) {
        return check(object, "id=" + id);
    }

    public static <T> T check(T object, String msg) {
        check(object != null, msg);
        return object;
    }

    public static void check(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }
}
