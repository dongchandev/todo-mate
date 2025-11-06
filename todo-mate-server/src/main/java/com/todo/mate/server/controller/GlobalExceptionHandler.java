package com.todo.mate.server.controller;

import com.todo.mate.server.controller.response.Response;
import com.todo.mate.server.domain.exception.CustomException;
import com.todo.mate.server.domain.exception.ExceptionCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Response<Void>> handleCustomException(CustomException e){
        ExceptionCode code = e.getExceptionCode();
        System.out.println(code.getStatus());
        return ResponseEntity
                .status(200)
                .body(Response.of(
                        HttpStatus.valueOf(code.getStatus()),
                        code.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Response<Void>> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity
                .status(200)
                .body(Response.of(
                        HttpStatus.NOT_FOUND,
                        e.getMessage()
                ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Response<Void>> handleNoSuchElementException(IllegalArgumentException e) {
        return ResponseEntity
                .status(200)
                .body(Response.of(
                        HttpStatus.BAD_REQUEST,
                        e.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Response<Void>> handleException(Exception e, HttpServletRequest request) {
        System.out.println(e.getMessage());
        return ResponseEntity
                .status(500)
                .body(Response.of(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.name()
                ));
    }

}