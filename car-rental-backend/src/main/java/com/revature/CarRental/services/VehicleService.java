package com.revature.CarRental.services;

import com.revature.CarRental.models.Location;
import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.repos.LocationDAO;
import com.revature.CarRental.repos.VehicleDAO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private VehicleDAO vd;
    private LocationDAO ld;

    @Autowired
    public VehicleService(LocationDAO ld, VehicleDAO vd) {
        this.vd = vd;
        this.ld = ld;
    }

    public List<Vehicle> getAllVehicles() {
        return vd.findAll();
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

    public Vehicle addVehicle(int id, Vehicle vehicle) {
        Optional<Location> optLocation = ld.findById(id);
        if (optLocation.isPresent()) {
            Location location = optLocation.get();
            vehicle.setLocation(location);
        } else {
            throw new EntityNotFoundException("No Location found with id: " + id);
        }
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


    public void updateVehicleAvailability(int id, Boolean availability) {
        Optional<Vehicle> optionalVehicle = vd.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            vehicle.setAvailable(availability);
            vd.save(vehicle);
        } else {
            throw new EntityNotFoundException("No Vehicle found with id: " + id);
        }
    }


}
