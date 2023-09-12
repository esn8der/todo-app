package com.rediense.todoapp.exception;

public class RegistrationException extends GeneralRuntimeException {

    public RegistrationException(String message){
        super(message);
    }
    public RegistrationException(String message, Throwable cause){
        super(message, cause);
    }
}
