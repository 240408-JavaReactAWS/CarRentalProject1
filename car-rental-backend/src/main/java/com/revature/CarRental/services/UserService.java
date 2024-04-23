package com.revature.CarRental.services;

import com.revature.CarRental.repos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.CarRental.models.User;

@Service
public class UserService {

    private UserDAO ud;

    @Autowired
    public UserService(UserDAO ud) {
        this.ud = ud;
    }

    public User createUser(User user) {
        return ud.save(user);
    }



}
