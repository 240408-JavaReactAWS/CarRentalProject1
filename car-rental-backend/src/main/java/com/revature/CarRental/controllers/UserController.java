package com.revature.CarRental.controllers;

import com.revature.CarRental.models.User;
import com.revature.CarRental.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import com.revature.CarRental.models.User;
import org.springframework.web.client.HttpClientErrorException;
import javax.security.auth.login.FailedLoginException;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;


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

    @PostMapping("/login")
    public ResponseEntity<User> loginHandler(@RequestBody User loginAttempt) {
        User user;
        try {
            user = us.login(loginAttempt);
        } catch (FailedLoginException e) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }

        return new ResponseEntity<>(user, OK);
    }

}
