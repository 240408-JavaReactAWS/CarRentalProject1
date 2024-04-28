package com.revature.CarRental.repos;

import com.revature.CarRental.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationDAO extends JpaRepository<Location, Integer> {

    /**
     * RETRIEVE ALL LOCATION BY CITY AND STATE
     */
    List<Location> findAllBylocationId(int locationId);


}
