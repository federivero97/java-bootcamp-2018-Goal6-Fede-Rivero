package com.bootcamp.fede.market.controller;

import com.bootcamp.fede.market.entity.Product;
import com.bootcamp.fede.market.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping("/product/all")
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @PostMapping("/product/new")
    public Product newProduct(@RequestParam(name = "name") String name,
                              @RequestParam(name = "price") Double price,
                              @RequestParam(name = "stock") Integer stock){
        Product existProduct = productRepository.findByName(name);
        if (existProduct==null){
            Product newProduct = new Product(name, price, stock);
            existProduct = newProduct;
        } else {
            existProduct.increaseStock(stock);
        }
        return productRepository.save(existProduct);
    }

    @GetMapping("/product/{name}/exist")
    public boolean existProduct(@PathVariable String name){
        return (productRepository.findByName(name)!=null);
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable Long id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @GetMapping("/product/find-by-name")
    public List<Product> getProducts(@RequestParam(name = "name") String name){
        List<Product> existProduct = productRepository.findLikeName(name);
        if (existProduct==null){
            throw (new ProductNotFoundByNameException(name));
        }
        return productRepository.findLikeName(name);
    }

    @PutMapping("/product/{id}/update")
    public Product updateProduct(@PathVariable Long id,
                                 @RequestParam String name,
                                 @RequestParam Double price,
                                 @RequestParam Integer stock) {
        if (price < 0 | stock < 0){
            throw new InvalidParamException("price or stock");
        } else {
            Product existProduct = productRepository.findByProductId(id);
            if (existProduct != null) {
                if (name!="") {
                    existProduct.setName(name);
                }
                if (price!=null){
                    existProduct.setPrice(price);
                }
                if (stock!=null){
                    existProduct.setStock(stock);
                }
                return productRepository.save(existProduct);
            } else {
                throw new ProductNotFoundException(id);
            }
        }
    }

    @PutMapping("/product/{id}/stock")
    public Product increaseStock(@PathVariable Long id,
                                 @RequestParam Integer stock){

        Product existProduct = productRepository.findByProductId(id);
        if (existProduct != null) {
            Integer actualStock = existProduct.getStock();
                if (actualStock < -stock) {
                    throw new InvalidParamException("stock");
                }
                existProduct.increaseStock(stock);
                return productRepository.save(existProduct);
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
