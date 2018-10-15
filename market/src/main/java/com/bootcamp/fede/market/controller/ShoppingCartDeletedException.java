package com.bootcamp.fede.market.controller;

public class ShoppingCartDeletedException extends RuntimeException {
    public ShoppingCartDeletedException(Long userId, Long productId){
        super("The product " + productId + " has been deleted from the user's " + userId +" shopping cart");
    }
}
