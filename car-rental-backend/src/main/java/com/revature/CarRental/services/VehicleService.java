package com.revature.CarRental.services;

import com.revature.CarRental.models.Location;
import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.repos.VehicleDAO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    private VehicleDAO vd;

    @Autowired
    public VehicleService(VehicleDAO vd) {
        this.vd = vd;
    }

    public Vehicle updateVehicleLocation(int id, Location location) {
        Optional<Vehicle> optionalVehicle = vd.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            vehicle.setLocation(location);
            vd.save(vehicle);
            return vehicle;
        }
        throw new EntityNotFoundException("No Vehicle found with id: " + id);
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        vd.save(vehicle);
        return vehicle;
    }

    public void removeVehicle(int id) {
        vd.deleteById(id);
    }

    public Vehicle updateVehicle(int id, Vehicle newVehicle) {
        Optional<Vehicle> optVehicle = vd.findById(id);
        if (optVehicle.isPresent()) {
            Vehicle vehicle = optVehicle.get();
            vehicle.setMake(newVehicle.getMake());
            vehicle.setModel(newVehicle.getModel());
            vehicle.setYear(newVehicle.getYear());
            vehicle.setColor(newVehicle.getColor());
            vehicle.setAvailable(newVehicle.getAvailable());
            vehicle.setLocation(newVehicle.getLocation());
            vd.save(vehicle);
            return vehicle;
        }
        throw new EntityNotFoundException("No Vehicle found with id: " + id);
    }

}
