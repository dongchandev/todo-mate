package com.todo.mate.server.domain.exception;

public interface ExceptionCode {

    int getStatus();

    String getExceptionName();

    String getMessage();

}