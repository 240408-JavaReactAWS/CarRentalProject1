package com.revature.CarRental.services;

import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.repos.VehicleDAO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class VehicleService {

    private VehicleDAO vd;

    @Autowired
    public VehicleService(VehicleDAO vd) {
        this.vd = vd;
    }

    public Vehicle updateVehicleLocation(int id, int locationId) {
        Optional<Vehicle> optionalVehicle = vd.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            vehicle.setLocationId(locationId);
            vd.save(vehicle);
            return vehicle;
        }
        throw new EntityNotFoundException("No Vehicle found with id: " + id);
    }

}
