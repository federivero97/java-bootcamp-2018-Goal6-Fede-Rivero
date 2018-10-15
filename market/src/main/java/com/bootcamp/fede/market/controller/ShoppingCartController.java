package com.bootcamp.fede.market.controller;

import com.bootcamp.fede.market.entity.ShoppingCart;
import com.bootcamp.fede.market.repository.ProductRepository;
import com.bootcamp.fede.market.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShoppingCartController {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ShoppingCartController(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository){
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/user/{userId}/shopping-cart")
    public List<ShoppingCart> getShoppingCart(@PathVariable Long userId){
        return shoppingCartRepository.findByUserId(userId);
    }

    @PostMapping("/user/{userId}/shopping-cart/new")
    public ShoppingCart newShoppingCart(@PathVariable Long userId,
                              @RequestParam(name = "productId") Long productId,
                              @RequestParam(name = "amount") Integer amount){
        if (amount < 1){
            throw new InvalidParamException("amount");
        }
        if (amount > productRepository.findByProductId(productId).getStock()){
            throw new ProductNotAvailableStockException();
        }
        ShoppingCart existInShoppingCart = shoppingCartRepository.existInShoppingCart(userId,productId);
        if (existInShoppingCart==null){
            ShoppingCart newShoppingCart = new ShoppingCart(userId, productId, amount);
            existInShoppingCart = newShoppingCart;
        } else {
            existInShoppingCart.increaseAmount(amount);
        }
        productRepository.findByProductId(productId).increaseStock(-amount);
        return shoppingCartRepository.save(existInShoppingCart);
    }

    @GetMapping("/user/{userId}/shopping-cart/{productId}/exist")
    public boolean existShoppingCart(@PathVariable Long userId,
                                     @PathVariable Long productId){
        return (shoppingCartRepository.existInShoppingCart(userId, productId)!=null);
    }

    @PutMapping("/user/{userId}/shopping-cart/{productId}/update-amount")
    public ShoppingCart updateAmount(@PathVariable Long userId,
                                     @PathVariable Long productId,
                                     @RequestParam Integer amount){

        ShoppingCart existInShoppingCart = shoppingCartRepository.existInShoppingCart(userId, productId);
        if (existInShoppingCart != null) {
            Integer originAmount = existInShoppingCart.getAmount();
            if (amount > 0) {
                if ((amount - originAmount) > productRepository.findByProductId(productId).getStock()){
                    throw new ProductNotAvailableStockException();
                } else {
                    existInShoppingCart.setAmount(amount);
                    productRepository.findByProductId(productId).increaseStock(originAmount - amount);
                }
            } else if (amount==0){
                productRepository.findByProductId(productId).increaseStock(originAmount);
                shoppingCartRepository.delete(existInShoppingCart);
                throw new ShoppingCartDeletedException(userId, productId);
            } else {
                throw new InvalidParamException("amount");
            }
        } else {
            throw new ShoppingcartNotFoundException(userId, productId);
        }
        return shoppingCartRepository.save(existInShoppingCart);
    }

    @PutMapping("/user/{userId}/shopping-cart/{productId}/amount")
    public ShoppingCart amount(@PathVariable Long userId,
                               @PathVariable Long productId,
                               @RequestParam Integer amount){
        ShoppingCart existInShoppingCart = shoppingCartRepository.existInShoppingCart(userId, productId);
        if (existInShoppingCart != null){
            Integer actualAmount = existInShoppingCart.getAmount();
            if (amount > 0) {
                if (amount > productRepository.findByProductId(productId).getStock()){
                    throw new ProductNotAvailableStockException();
                } else {
                    existInShoppingCart.increaseAmount(amount);
                    productRepository.findByProductId(productId).increaseStock(-amount);
                }
            } else {
                if (-amount >= actualAmount){
                    if (-amount==actualAmount){
                        productRepository.findByProductId(productId).increaseStock(-amount);
                        shoppingCartRepository.delete(existInShoppingCart);
                        throw new ShoppingCartDeletedException(userId, productId);
                    } else {
                        throw new InvalidParamException("amount");
                    }
                } else {
                    existInShoppingCart.increaseAmount(amount);
                    productRepository.findByProductId(productId).increaseStock(-amount);
                }
            }
        } else {
            throw new ShoppingcartNotFoundException(userId, productId);
        }
        return shoppingCartRepository.save(existInShoppingCart);

    }

    @GetMapping("/user/{userId}/shopping-cart/value")
    public Double getShoppingCartValue(@PathVariable Long userId){
        Double value = 0.0;
        List<ShoppingCart> shoppingCartsList= shoppingCartRepository.findByUserId(userId);
        for (ShoppingCart shoppingCart: shoppingCartsList){
            value += (productRepository.findByProductId(shoppingCart.getProductId()).getPrice() * shoppingCart.getAmount());
        }
        return value;
    }

    @DeleteMapping("/user/{userId}/shopping-cart/{productId}")
    public void deleteShoppingCart(@PathVariable Long userId,
                              @PathVariable Long productId) {
        ShoppingCart existInShoppingCart = shoppingCartRepository.existInShoppingCart(userId, productId);
        productRepository.findByProductId(productId).increaseStock(existInShoppingCart.getAmount());
        shoppingCartRepository.delete(existInShoppingCart);
    }
}
