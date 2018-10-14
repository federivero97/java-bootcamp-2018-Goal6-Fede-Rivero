package com.bootcamp.fede.market.service;

import com.bootcamp.fede.market.entity.User;
import com.bootcamp.fede.market.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService {


    private UserController userController;

    @Autowired
    public UserService(UserController userController){
        this.userController = userController;
    }

    public User newUser(User user){
        return userController.newUser(user.getName(),user.getPassword(),user.getEmail());
    }

    public User getUser(Long id){
        return userController.getUser(id);
    }

    public List<User> getAllUsers(){
        return userController.getAllUser();
    }

    public User updateUser(String newName, String newPassword, String newEmail, Long id){
        return userController.updateUser(newName,newPassword,newEmail,id);
    }

    public void deleteUser(Long id) {
        userController.deleteUser(id);
    }
}
