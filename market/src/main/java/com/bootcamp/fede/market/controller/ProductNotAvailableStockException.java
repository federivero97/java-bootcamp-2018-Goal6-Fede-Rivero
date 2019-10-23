package com.bootcamp.fede.market.controller;

public class ProductNotAvailableStockException extends RuntimeException {
    public ProductNotAvailableStockException() {
        super("Sorry. This product don't have enough stock, please try with a smaller amount");
    }

}
