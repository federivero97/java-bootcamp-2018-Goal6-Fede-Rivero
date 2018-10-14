package com.bootcamp.fede.market.repository;

import com.bootcamp.fede.market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User,Long>{

    @Query("SELECT user FROM User user WHERE user.id =:id")
    @Transactional(readOnly = true)
    User findById(@Param("id") Integer id);

}