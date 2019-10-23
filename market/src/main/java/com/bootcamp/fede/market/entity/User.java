package com.bootcamp.fede.market.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user")
public class User {

    @Column(name = "id")
    @ApiModelProperty(notes = "The database generated user ID")
    private @Id @GeneratedValue Long id;

    @Column(name = "user_name")
    @ApiModelProperty(notes = "The user name")
    private String name;

    @Column(name = "password")
    @ApiModelProperty(notes = "The user password")
    private String password;

    @Column(name = "email")
    @ApiModelProperty(notes = "The user email")
    private String email;

    public User() {
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
