package com.revature.CarRental.services;

import com.revature.CarRental.models.User;
import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.repos.UserDAO;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.CarRental.models.User;
import javax.security.auth.login.FailedLoginException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserDAO ud;

    @Autowired
    public UserService(UserDAO ud) {
        this.ud = ud;
    }

    public User createUser(User credentials) throws EntityExistsException {
        Optional<User> optionalUser = ud.findByUsername(credentials.getUsername());
        if (optionalUser.isPresent()) {
            throw new EntityExistsException(credentials.getUsername()+" already exists");
        }
        return ud.save(credentials);
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

    public Vehicle getCurrentCar(int userId) {
        Optional<User> optionalUser = ud.findById(userId);
        if (optionalUser.isPresent()) {
            if(optionalUser.get().getCurrentCar() == null){
                throw new EntityNotFoundException("User with id has no current car : " + userId);
            }
            return optionalUser.get().getCurrentCar();
        } else {
            throw new EntityNotFoundException("No User found with id: " + userId);
        }
    }

    public void updateCurrentCar(int userId, Vehicle vehicle) {
        Optional<User> optionalUser = ud.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setCurrentCar(vehicle);
            ud.save(user);
        }else {
            throw new EntityNotFoundException("No User found with id: " + userId);
        }

    }

    public List<User> getAllUsers() {
        return ud.findAll();
    }

}
