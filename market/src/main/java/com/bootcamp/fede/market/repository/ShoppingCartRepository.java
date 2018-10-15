package com.bootcamp.fede.market.repository;

import com.bootcamp.fede.market.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long>{

    @Query("SELECT shoppingCart FROM ShoppingCart shoppingCart WHERE shoppingCart.userId =:id")
    @Transactional(readOnly = true)
    List <ShoppingCart> findByUserId(@Param("id") Long id);

    @Query("SELECT shoppingCart FROM ShoppingCart shoppingCart WHERE shoppingCart.userId =:userId and shoppingCart.productId  =:productId")
    @Transactional(readOnly = true)
    ShoppingCart existInShoppingCart(@Param("userId") Long userId, @Param("productId") Long productId);

}
