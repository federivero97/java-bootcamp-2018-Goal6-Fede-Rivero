package com.bootcamp.fede.market.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidParamAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidParamException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String InvalidParamHandler(InvalidParamException ex) {
        return ex.getMessage();
    }
}
