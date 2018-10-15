package com.bootcamp.fede.market.service;

import com.bootcamp.fede.market.controller.ProductController;
import com.bootcamp.fede.market.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductService {

    private final ProductController productController;

    @Autowired
    public ProductService(ProductController productController){
        this.productController = productController;
    }

    public Product newProduct(Product product){
        return productController.newProduct(product.getName(), product.getPrice(), product.getStock());
    }

    public boolean existProduct(Product product){return productController.existProduct(product.getName());}

    public Product getProduct(Long id){
        return productController.getProduct(id);
    }

    public List<Product> getProducts(String name){
        return productController.getProducts(name);
    }

    public List<Product> getAllProducts(){
        return productController.getAllProducts();
    }

    public Product updateProduct(Product product){
        return productController.updateProduct(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }

    public Product increaseStock(Product product, Integer amount){
        return productController.increaseStock(product.getId(), amount);
    }

    public void deleteUser(Long id) {
        productController.deleteProduct(id);
    }
}
