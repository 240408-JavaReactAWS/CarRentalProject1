package com.revature.CarRental.repos;

import com.revature.CarRental.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {



}
