package com.todo.mate.server.domain.exception;

public class InvalidValue extends CustomException {
    public InvalidValue(TodoExceptionCode code) {
        super(code);
    }
}
