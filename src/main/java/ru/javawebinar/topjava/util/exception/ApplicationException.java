package ru.javawebinar.topjava.util.exception;

public class ApplicationException extends RuntimeException {

    private final ErrorType type;
    private final String msgCode;

    public ApplicationException(String msgCode, ErrorType type) {
        this.msgCode = msgCode;
        this.type = type;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public ErrorType getType() {
        return type;
    }
}
