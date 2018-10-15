package com.bootcamp.fede.market.controller;

public class UserNotFoundByNameException extends RuntimeException {

    UserNotFoundByNameException(String name) {
        super("Could not find employee " + name);
    }

}