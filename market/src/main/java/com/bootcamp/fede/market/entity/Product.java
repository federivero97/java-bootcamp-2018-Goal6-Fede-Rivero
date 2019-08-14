package com.bootcamp.fede.market.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "product")
public class Product {

    @Column(name = "id")
    @ApiModelProperty(notes = "The database generated product ID")
    private @Id @GeneratedValue Long id;

    @Column(name = "product_name")
    @ApiModelProperty(notes = "The product name")
    private String name;

    @Column(name = "price")
    @ApiModelProperty(notes = "The product price")
    private Double price;

    @Column(name = "stock")
    @ApiModelProperty(notes = "The product amount available")
    private Integer stock;

    public Product(){
    }

    public Product(String name, Double price, Integer stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock){
        this.stock = stock;
    }

    public void increaseStock(Integer stock){
        this.stock += stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
