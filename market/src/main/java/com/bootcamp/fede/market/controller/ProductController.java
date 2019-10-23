package com.bootcamp.fede.market.controller;

import com.bootcamp.fede.market.entity.Product;
import com.bootcamp.fede.market.repository.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@Api(value="onlinestore", description="Operations pertaining to products in Market API")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @ApiOperation(value = "View a list of available products", response = List.class)
    @GetMapping("/product/all")
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @ApiOperation(value = "Create a new product", response = Product.class)
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
    @ApiOperation(value = "Ask if a product exists with an ID", response = boolean.class)
    @GetMapping("/product/{name}/exist")
    public boolean existProduct(@PathVariable String name){
        return (productRepository.findByName(name)!=null);
    }

    @ApiOperation(value = "Search a product with an ID", response = Product.class)
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable Long id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @ApiOperation(value = "Search products with a name", response = List.class)
    @GetMapping("/product/find-by-name")
    public List<Product> getProducts(@RequestParam(name = "name") String name){
        List<Product> existProduct = productRepository.findLikeName(name);
        if (existProduct==null){
            throw (new ProductNotFoundByNameException(name));
        }
        return productRepository.findLikeName(name);
    }

    @ApiOperation(value = "Update all product's attributes, searching that with an ID", response = Product.class)
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

    @ApiOperation(value = "Increase or decrease product's stock, searching that with an ID", response = Product.class)
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

    @ApiOperation(value = "Delete a product searching that with an ID")
    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
