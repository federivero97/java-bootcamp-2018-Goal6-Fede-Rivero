package com.bootcamp.fede.market.controller;

import com.bootcamp.fede.market.entity.User;
import com.bootcamp.fede.market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/user/all")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @PostMapping("/user/new")
    public User newUser(@RequestParam(name = "name") String name,
                        @RequestParam(name = "password") String password,
                        @RequestParam(name = "email") String email){
        User newUser = new User(name, password,email);
        return userRepository.save(newUser);
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/user/{name}/exist")
    public boolean existUser(@PathVariable String name){
        return (userRepository.findByName(name)!=null);
    }

    @GetMapping("/user/find-by-name/{name}")
    public User getUser(@PathVariable String name){
        User existUser = userRepository.findByName(name);
        if (existUser != null){
            return existUser;
        } else {
            throw new UserNotFoundByNameException(name);
        }
    }

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

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
