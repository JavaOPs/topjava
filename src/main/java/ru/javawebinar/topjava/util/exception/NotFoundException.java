package ru.javawebinar.topjava.util.exception;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
