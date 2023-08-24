package ru.javawebinar.topjava.util.exception;

public class UpdateRestrictionException extends ApplicationException {
    public static final String EXCEPTION_UPDATE_RESTRICTION = "exception.user.updateRestriction";

    public UpdateRestrictionException() {
        super(EXCEPTION_UPDATE_RESTRICTION, ErrorType.VALIDATION_ERROR);
    }
}
