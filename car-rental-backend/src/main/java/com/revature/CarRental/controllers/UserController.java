package com.revature.CarRental.controllers;

import com.revature.CarRental.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.revature.CarRental.models.User;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService us;

    @Autowired
    public UserController(UserService us) {
        this.us = us;
    }

    @PostMapping
    public User registerUserHandler(@RequestBody User user) {
        return us.createUser(user);
    }

}
