package com.toby.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ControllerErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorVo exceptionHandler(Exception e){
        return new ErrorVo("E999",e.getMessage());
    }

}
