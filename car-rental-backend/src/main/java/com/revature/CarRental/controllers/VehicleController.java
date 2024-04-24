package com.revature.CarRental.controllers;


import com.revature.CarRental.models.Location;
import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.services.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private VehicleService vs;

    @Autowired
    public VehicleController(VehicleService vs) {
        this.vs = vs;
    }

    @PatchMapping("{id}")
    public ResponseEntity<Vehicle> updateVehicleLocationHandler(@PathVariable int id, @RequestBody Location newLocation) {
        Vehicle vehicle;
        try {
            vehicle = vs.updateVehicleLocation(id, newLocation);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(vehicle, OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Vehicle> addVehicleHandler(@RequestBody Vehicle vehicle) {
        vs.addVehicle(vehicle);
        return new ResponseEntity<>(vehicle, CREATED);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Vehicle> removeVehicleHandler(@PathVariable int id) {
        try {
            vs.removeVehicle(id);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<Vehicle> updateVehicleHandler(@RequestBody Vehicle vehicle) {
        try {
            vs.updateVehicle(vehicle);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(vehicle, OK);
    }
}
