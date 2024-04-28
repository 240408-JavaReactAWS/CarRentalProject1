package com.revature.CarRental.services;

import com.revature.CarRental.models.Location;
import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.repos.LocationDAO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private LocationDAO ld;

    @Autowired
    public LocationService(LocationDAO ld) {
        this.ld = ld;
    }


    public List<Location> getAllLocations() {
        return ld.findAll();
    }

    public Location getLocationById(int id) {
        Optional<Location> optLocation = ld.findById(id);
        if(optLocation.isPresent()) {
            return optLocation.get();
        }
        throw new EntityNotFoundException("No Location found with id: " + id);
    }

    /**
     * VEHICLE RETRIEVING - ALL VEHICLES AT A LOCATION
     * @return `List of Vehicles` if exist `empty List` otherwise
     */
    public List<Vehicle> getAllVehiclesAtLocation(int locationId) {

        List<Location> locations = ld.findAllBylocationId(locationId);
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        locations.forEach( (loc) -> {vehicles.addAll( loc.getVehicleStock());} );
        return vehicles;
    }

    public Location addLocation(Location location) {
        return ld.save(location);
    }

    public void removeLocation(int id) {
        ld.deleteById(id);
    }

    public Location updateLocation(int id, Location newlocation) {
        Optional<Location> optLocation = ld.findById(id);
        if(optLocation.isPresent()) {
            Location location = optLocation.get();
            location.setCity(newlocation.getCity());
            location.setState(newlocation.getState());
            location.setPostalCode(newlocation.getPostalCode());
            location.setStreetAddress(newlocation.getStreetAddress());
            ld.save(location);
            return location;
        }
        throw new EntityNotFoundException("No Location found with id: " + id);
    }
}
