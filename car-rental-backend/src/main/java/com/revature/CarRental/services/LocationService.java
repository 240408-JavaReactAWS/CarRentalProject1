package com.revature.CarRental.services;

import com.revature.CarRental.models.Location;
import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.repos.LocationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    private LocationDAO ld;

    @Autowired
    public LocationService(LocationDAO ld) {
        this.ld = ld;
    }


    /**
     * VEHICLE RETRIEVING - ALL VEHICLES AT A LOCATION
     * @return `List of Vehicles` if exist `empty List` otherwise
     */
    public List<Vehicle> getAllVehiclesAtLocation(String city, String state) {

        List<Location> locations = ld.findAllByCityAndState(city, state);
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        locations.forEach( (loc) -> {vehicles.addAll( loc.getVehicleStock());} );
        return vehicles;
    }

    public void addLocation(Location location) {
        ld.save(location);
    }

    public void removeLocation(int id) {
        ld.deleteById(id);
    }

    public void updateLocation(Location location) {
        ld.save(location);
    }
}
