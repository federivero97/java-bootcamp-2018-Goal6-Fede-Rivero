package com.bootcamp.fede.market.controller;

public class ProductNotFoundByIdException extends RuntimeException {

    ProductNotFoundByIdException(Long id) {
        super("Could not find product " + id);
    }


}