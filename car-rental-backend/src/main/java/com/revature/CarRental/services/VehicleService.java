package com.revature.CarRental.services;

import com.revature.CarRental.repos.VehicleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private VehicleDAO vd;

    @Autowired
    public VehicleService(VehicleDAO vd) {
        this.vd = vd;
    }



}
