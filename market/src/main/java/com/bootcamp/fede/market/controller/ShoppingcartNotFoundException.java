package com.bootcamp.fede.market.controller;

public class ShoppingcartNotFoundException extends RuntimeException {

    ShoppingcartNotFoundException(Long userId, Long productId) {
        super("Could not find product " + productId + " in user shopping cart of user " + userId);
    }

}
