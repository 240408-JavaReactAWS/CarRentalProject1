package com.revature.CarRental.services;

import com.revature.CarRental.repos.LocationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private LocationDAO ld;

    @Autowired
    public LocationService(LocationDAO ld) {
        this.ld = ld;
    }



}
