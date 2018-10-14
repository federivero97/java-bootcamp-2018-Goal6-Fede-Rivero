package com.bootcamp.fede.market.service;

import com.bootcamp.fede.market.controller.ProductController;
import com.bootcamp.fede.market.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductService {

    private ProductController productController;

    @Autowired
    public ProductService(ProductController productController){
        this.productController = productController;
    }

    public Product newProduct(Product product){
        return productController.newProduct(product.getName(),product.getPrice(),product.getStock());
    }

    public Product getProduct(Long id){
        return productController.getProduct(id);
    }

    public Product getProduct(String name){
        return productController.getProduct(name);
    }

    public List<Product> getAllProducts(){
        return productController.getAllProducts();
    }

    public Product updateProduct(String newName, Double newPrice, Integer newStock, Long id){
        return productController.updateProduct(newName,newPrice,newStock,id);
    }

    public Product increaseStock(Integer amount, Long id){
        return productController.increaseStock(amount,id);
    }

    public void deleteUser(Long id) {
        productController.deleteProduct(id);
    }
}
