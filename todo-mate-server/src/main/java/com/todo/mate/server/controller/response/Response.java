package com.todo.mate.server.controller.response;

import org.springframework.http.HttpStatus;

public class Response<T> {

    private final int status;
    private final String message;
    private final T data;

    private Response(HttpStatus status, String message, T data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    public static <T> Response<T> of(HttpStatus status, String message, T data) {
        return new Response<>(status, message, data);
    }

    public static <T> Response<T> of(HttpStatus status, String message) {
        return new Response<>(status, message, null);
    }

    public static <T> Response<T> ok(String message, T data) {
        return new Response<>(HttpStatus.OK, message, data);
    }

    public static <T> Response<T> ok(String message) {
        return new Response<>(HttpStatus.OK, message, null);
    }

    public static <T> Response<T> created(String message) {
        return new Response<>(HttpStatus.CREATED, message, null);
    }

    public static <T> Response<T> created(String message, T data) {
        return new Response<>(HttpStatus.CREATED, message, data);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}