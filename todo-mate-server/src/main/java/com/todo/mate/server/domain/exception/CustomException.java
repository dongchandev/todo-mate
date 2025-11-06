package com.todo.mate.server.domain.exception;

public class CustomException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public CustomException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
