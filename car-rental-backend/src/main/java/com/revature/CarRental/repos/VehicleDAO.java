package com.revature.CarRental.repos;

import com.revature.CarRental.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleDAO extends JpaRepository<Vehicle, Integer> {



}
