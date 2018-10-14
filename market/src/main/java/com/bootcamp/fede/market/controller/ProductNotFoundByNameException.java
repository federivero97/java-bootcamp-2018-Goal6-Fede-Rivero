package com.bootcamp.fede.market.controller;

public class ProductNotFoundByNameException extends RuntimeException {

    ProductNotFoundByNameException(String name) {
        super("Could not find product " + name);
    }

}
