package com.rediense.todoapp.exception;

public class DataNotFoundException extends GeneralRuntimeException {

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
