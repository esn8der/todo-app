package com.rediense.todoapp.exception;

public class GeneralRuntimeException extends RuntimeException{

    private final String message;

    public GeneralRuntimeException(String message) {
        super(message);
        this.message = message;
    }

    public GeneralRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }
}
