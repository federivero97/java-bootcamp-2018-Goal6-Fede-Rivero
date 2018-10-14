package com.bootcamp.fede.market.repository;

import com.bootcamp.fede.market.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface ProductRepository extends JpaRepository<Product,Long>{

    @Query("SELECT product FROM Product product WHERE product.id =:id")
    @Transactional(readOnly = true)
    Product findById(@Param("id") int id);

    @Query("SELECT DISTINCT product FROM Product product WHERE product.name LIKE :name%")
    @Transactional(readOnly = true)
    Product findByName(@Param("name") String name);



}