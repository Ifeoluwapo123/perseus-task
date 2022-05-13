package com.perseus.task.exception;

public class ApiConflictException extends RuntimeException{
    public ApiConflictException(String message) {
        super(message);
    }
}
