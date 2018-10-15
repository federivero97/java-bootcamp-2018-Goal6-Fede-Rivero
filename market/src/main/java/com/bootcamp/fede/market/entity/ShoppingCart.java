package com.bootcamp.fede.market.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Column(name = "user_id")
    private @Id Long userId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "amount")
    private Integer amount;

    public ShoppingCart() {
    }

    public ShoppingCart(Long userId, Long productId, Integer amount) {
        this.userId = userId;
        this.productId = productId;
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void increaseAmount(Integer amount){
        this.amount += amount;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "productId=" + productId +
                ", amount=" + amount +
                '}';
    }
}
