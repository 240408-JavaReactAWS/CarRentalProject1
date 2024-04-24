package com.revature.CarRental.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "locations")
public class Location {

    // Data Fields

    @Id
    @Column(name = "locationId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationId;

    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;

    @OneToMany(mappedBy = "location")
    @JsonManagedReference
    private List<Vehicle> vehicleStock;

    // Constructors

    public Location() {
    }

    public Location(String streetAddress, String city, String state, String postalCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public Location(String streetAddress, String city, String state, String postalCode, List<Vehicle> vehicleStock) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.vehicleStock = vehicleStock;
    }

    // Getters & Setters
    
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<Vehicle> getVehicleStock() {
        return vehicleStock;
    }

    public void setVehicleStock(List<Vehicle> vehicleStock) {
        this.vehicleStock = vehicleStock;
    }
}
