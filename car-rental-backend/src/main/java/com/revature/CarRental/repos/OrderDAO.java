package com.revature.CarRental.repos;

import com.revature.CarRental.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDAO extends JpaRepository<Order, Integer> {



}
