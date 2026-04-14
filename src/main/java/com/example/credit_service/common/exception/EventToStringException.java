package com.example.credit_service.common.exception;

public class EventToStringException extends RuntimeException {
    public EventToStringException(String message, Throwable e) {
        super("Could not write event to string with ObjectMapper: " + message, e);
    }
}
