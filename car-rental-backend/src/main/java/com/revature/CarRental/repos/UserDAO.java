package com.revature.CarRental.repos;

import com.revature.CarRental.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
