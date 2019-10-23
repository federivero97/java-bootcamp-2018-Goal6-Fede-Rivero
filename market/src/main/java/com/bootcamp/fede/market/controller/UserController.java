package com.bootcamp.fede.market.controller;

import com.bootcamp.fede.market.entity.User;
import com.bootcamp.fede.market.repository.UserRepository;
import com.bootcamp.fede.market.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value="onlinestore", description="Operations pertaining to users in Market API")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "View a list of users", response = List.class)
    @GetMapping("/user/all")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @ApiOperation(value = "Create a new product", response = User.class)
    @PostMapping("/user/new")
    public User newUser(@RequestParam(name = "name") String name,
                        @RequestParam(name = "password") String password,
                        @RequestParam(name = "email") String email){
        User newUser = new User(name, password,email);
        return userRepository.save(newUser);
    }

    @ApiOperation(value = "Search an user with an ID",response = User.class)
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id){
        User existUser = userRepository.findByUserId(id);
        if (existUser!=null){
            return  existUser;
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @ApiOperation(value = "Ask if a product exists with an ID", response = boolean.class)
    @GetMapping("/user/{name}/exist")
    public boolean existUser(@PathVariable String name){
        return (userRepository.findByName(name)!=null);
    }

    @ApiOperation(value = "Search an user with a name", response = User.class)
    @GetMapping("/user/find-by-name/{name}")
    public User getUser(@PathVariable String name){
        User existUser = userRepository.findByName(name);
        if (existUser != null){
            return existUser;
        } else {
            throw new UserNotFoundByNameException(name);
        }
    }

    @ApiOperation(value = "Update all user's attributes, searching that with an ID", response = User.class)
    @PutMapping("/user/{id}/update")
    public User updateUser(@PathVariable Long id,
                           @RequestParam String name,
                           @RequestParam String password,
                           @RequestParam String email) {
        return userRepository.findById(id)
                .map(user -> {
                    if (name!="") {
                        user.setName(name);
                    }
                    if (password!=""){
                        user.setPassword(password);
                    }
                    if (email!=""){
                        user.setEmail(email);
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @ApiOperation(value = "Delete an user searching that with an ID")
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
