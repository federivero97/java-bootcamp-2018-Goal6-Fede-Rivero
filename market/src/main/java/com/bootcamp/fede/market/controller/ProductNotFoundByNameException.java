package com.bootcamp.fede.market.controller;

public class ProductNotFoundByName extends RuntimeException {

    ProductNotFoundByName(String name) {
        super("Could not find product " + name);
    }

}
