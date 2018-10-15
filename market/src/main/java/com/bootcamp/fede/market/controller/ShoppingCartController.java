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
            Integer orgirinAmount = existInShoppingCart.getAmount();
            if ((amount - orgirinAmount) > productRepository.findByProductId(productId).getStock()){
                throw new ProductNotAvailableStockException();
            }
            if (amount > 0) {
                existInShoppingCart.setAmount(amount);
                productRepository.findByProductId(productId).increaseStock(orgirinAmount - amount);
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
            if (amount > productRepository.findByProductId(productId).getStock()){
                throw new ProductNotAvailableStockException();
            }
            Integer actualAmount = existInShoppingCart.getAmount();
            if (amount > 0) {
                productRepository.findByProductId(productId).increaseStock(-amount);
                existInShoppingCart.increaseAmount(amount);
            } else {
                productRepository.findByProductId(productId).increaseStock(amount);
                existInShoppingCart.setAmount(0);
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
