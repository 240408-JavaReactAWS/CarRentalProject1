package com.revature.CarRental.controllers;


import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.services.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private VehicleService vs;

    @Autowired
    public VehicleController(VehicleService vs) {
        this.vs = vs;
    }

    @PatchMapping("{id}")
    public ResponseEntity<Vehicle> updateVehicleLocationHandler(@PathVariable int id, @RequestBody int newLocationId) {
        Vehicle vehicle;
        try {
            vehicle = vs.updateVehicleLocation(id, newLocationId);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(vehicle, OK);
    }

}
