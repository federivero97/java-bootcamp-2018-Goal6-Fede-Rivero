package com.bootcamp.fede.market.controller;

public class InvalidParamException extends RuntimeException {
    public InvalidParamException(String var){super("The variable " + var + " is invalid");}
}
