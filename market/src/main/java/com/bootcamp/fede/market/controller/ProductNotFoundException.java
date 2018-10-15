package com.bootcamp.fede.market.controller;

public class ProductNotFoundException extends RuntimeException {

    ProductNotFoundException(Long id) {
        super("Could not find product " + id);
    }


}