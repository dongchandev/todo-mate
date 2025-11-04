package com.todo.mate.server.domain.exception;

public class InvalidContent extends RuntimeException {
    public InvalidContent(String message) {
        super(message);
    }
}
