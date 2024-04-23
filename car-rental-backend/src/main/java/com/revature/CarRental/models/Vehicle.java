package com.revature.CarRental.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    // Data Fields

    @Id
    @Column (name = "vehicleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String color;
    private String make;
    private String model;
    private String year;

    @ManyToOne
    private Location location;

    private Boolean isAvailable;

    // Constructors

    public Vehicle() {
    }

    public Vehicle(String color, String make, String model, String year, Location location) {
        this.color = color;
        this.make = make;
        this.model = model;
        this.year = year;
        this.location = location;
        this.isAvailable = true;
    }

    // Setters and Getters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @JsonProperty(value="isAvailable")
    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
