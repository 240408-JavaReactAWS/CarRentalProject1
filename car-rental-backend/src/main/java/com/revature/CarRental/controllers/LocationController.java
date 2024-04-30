package com.revature.CarRental.controllers;

import com.revature.CarRental.models.Location;
import com.revature.CarRental.models.User;
import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.services.LocationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH})
public class LocationController {

    private LocationService ls;

    @Autowired
    public LocationController(LocationService ls) {
        this.ls = ls;
    }

    /**
     * USER - VIEW ALL LOCATIONS
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Location> viewAllLocationsHandler() {
        return ls.getAllLocations();
    }

    /**
     * USER - VIEW ALL VEHICLES AT A LOCATION
     */
    @GetMapping("/{locationId}/vehicles")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Vehicle> viewAllVehiclesAtLocationHandler(@PathVariable int locationId) {
        return ls.getAllVehiclesAtLocation(locationId); // ls = LocationService
    }

    /**
     * ADMIN - ADD LOCATION
     */
    //Modify this function to throw an error if the location already exists.
    @PostMapping("/add")
    public ResponseEntity<Location> addLocationHandler(@RequestBody Location location, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getAdmin()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else if (ls.addLocation(location) == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Location addedLoc = ls.addLocation(location);
        return new ResponseEntity<>(addedLoc, HttpStatus.CREATED);
    }

    /**
     * ADMIN - REMOVE LOCATION
     */
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Location> removeLocationHandler(@PathVariable int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getAdmin()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            ls.removeLocation(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * ADMIN - UPDATE LOCATION
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Location> updateLocationHandler(@PathVariable int id, @RequestBody Location location, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getAdmin()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            ls.updateLocation(id, location);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(location, HttpStatus.OK);
    }


}
