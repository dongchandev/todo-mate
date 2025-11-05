package com.todo.mate.server.domain.exception;

public class InvalidContent extends CustomException {
    public InvalidContent(TodoExceptionCode code) {
        super(code);
    }
}
