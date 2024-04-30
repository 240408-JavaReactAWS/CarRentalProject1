package com.revature.CarRental.controllers;

import com.revature.CarRental.models.User;
import com.revature.CarRental.services.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import com.revature.CarRental.models.User;
import org.springframework.web.client.HttpClientErrorException;
import javax.security.auth.login.FailedLoginException;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH})
public class UserController {

    private UserService us;

    @Autowired
    public UserController(UserService us) {
        this.us = us;
    }

    @PostMapping("register")
    public ResponseEntity<User> registerNewUserHandler(@RequestBody User credentials, HttpSession session){
        User newUser;
        try{
            newUser = us.createUser(credentials);
            session.setAttribute("user", newUser); // Store the user in the session
        } catch (EntityExistsException e){
            return new ResponseEntity<>(BAD_REQUEST);
        }
        return new ResponseEntity<>(newUser, CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginHandler(@RequestBody User loginAttempt, HttpSession session) {
        User user;
        try {
            user = us.login(loginAttempt);
            session.setAttribute("user", user); // Store the user in the session
        } catch (FailedLoginException e) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<User> logoutHandler(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUserHandler(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        return new ResponseEntity<>(user, OK);
    }

    /**
     * ADMIN - VIEW ALL USERS
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getAdmin()) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        return new ResponseEntity<>(us.getAllUsers(), OK);
    }

    // Validate Session
    @GetMapping("/session")
    public ResponseEntity<User> validateSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println(user);
        if (user == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        return new ResponseEntity<>(user, OK);
    }

    // Verify if User with Credentials is Admin
    @GetMapping("/admin")
    public ResponseEntity<Boolean> validateAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        } else if (!user.getAdmin()) {
            return new ResponseEntity<>(FORBIDDEN);
        } else {
            return new ResponseEntity<>(user.getAdmin(), OK);
        }
    }

    // Verify if User with Credentials has a Car
    @GetMapping("/hascar")
    public ResponseEntity<Boolean> validateHasCar(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        } else if (user.getCurrentCar() == null) {
            return new ResponseEntity<>(NOT_FOUND);
        } else {
            return new ResponseEntity<>(true, OK);
        }
    }




}
