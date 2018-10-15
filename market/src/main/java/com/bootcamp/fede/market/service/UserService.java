package com.bootcamp.fede.market.service;

import com.bootcamp.fede.market.entity.User;
import com.bootcamp.fede.market.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService {


    private final UserController userController;

    @Autowired
    public UserService(UserController userController){
        this.userController = userController;
    }

    public User newUser(User user){
        return userController.newUser(user.getName(), user.getPassword(), user.getEmail());
    }

    public boolean existUser(User user) { return userController.existUser(user.getName());}

    public User getUser(Long id){
        return userController.getUser(id);
    }

    public User getUser(String name){
        return userController.getUser(name);
    }

    public List<User> getAllUsers(){
        return userController.getAllUser();
    }

    public User updateUser(User user){
        return userController.updateUser(user.getId(), user.getName(), user.getPassword(), user.getEmail());
    }

    public void deleteUser(Long id) {
        userController.deleteUser(id);
    }
}
