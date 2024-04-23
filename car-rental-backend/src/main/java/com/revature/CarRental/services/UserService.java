package com.revature.CarRental.services;

import com.revature.CarRental.repos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    private UserDAO ud;

    @Autowired
    public UserService(UserDAO ud) {
        this.ud = ud;
    }



}
