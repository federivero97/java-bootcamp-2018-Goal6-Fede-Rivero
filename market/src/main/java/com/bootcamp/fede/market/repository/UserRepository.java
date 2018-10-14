package com.bootcamp.fede.market.repository;

import com.bootcamp.fede.market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{
}