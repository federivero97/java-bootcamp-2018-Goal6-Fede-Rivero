package com.bootcamp.fede.market.service;

import com.bootcamp.fede.market.controller.ShoppingCartController;
import com.bootcamp.fede.market.entity.ShoppingCart;
import com.bootcamp.fede.market.entity.User;

import java.util.List;

public class ShoppingCartService {

    private final ShoppingCartController shoppingCartController;

    public ShoppingCartService(ShoppingCartController shoppingCartController){
        this.shoppingCartController = shoppingCartController;
    }

    public List<ShoppingCart> getShoppingCart(User user){return shoppingCartController.getShoppingCart(user.getId());}

    public ShoppingCart newShoppingCart(ShoppingCart shoppingCart){
        return shoppingCartController.newShoppingCart(shoppingCart.getUserId(), shoppingCart.getProductId(), shoppingCart.getAmount());
    }

    public boolean existShoppingCart(ShoppingCart shoppingCart){
        return shoppingCartController.existShoppingCart(shoppingCart.getUserId(), shoppingCart.getProductId());
    }

    public ShoppingCart updateAmount(ShoppingCart shoppingCart){
        return shoppingCartController.updateAmount(shoppingCart.getUserId(), shoppingCart.getProductId(), shoppingCart.getAmount());
    }

    public ShoppingCart amount(ShoppingCart shoppingCart, Integer amount){
        return shoppingCartController.amount(shoppingCart.getUserId(), shoppingCart.getProductId(), amount);
    }

    public Double getShoppingCartValue(ShoppingCart shoppingCart){
        return shoppingCartController.getShoppingCartValue(shoppingCart.getUserId());
    }

    public void deleteShoppingCart(ShoppingCart shoppingCart){
        shoppingCartController.deleteShoppingCart(shoppingCart.getUserId(), shoppingCart.getProductId());
    }
}
