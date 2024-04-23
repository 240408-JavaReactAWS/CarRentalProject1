package com.revature.CarRental.services;

import com.revature.CarRental.models.User;
import com.revature.CarRental.repos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.login.FailedLoginException;
import java.util.Optional;

public class UserService {

    private UserDAO ud;

    @Autowired
    public UserService(UserDAO ud) {
        this.ud = ud;
    }

    public User login(User loginAttempt) throws FailedLoginException {
        Optional<User> optionalUser = ud.findByUsername(loginAttempt.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(loginAttempt.getPassword())) {
                return user;
            }
        }

        throw new FailedLoginException("Incorrect Username or Password");
    }

}
