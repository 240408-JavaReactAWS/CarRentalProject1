package com.revature.models;


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
    @JoinColumn(name = "location_id")
    private int locationId;

    private Boolean isAvailable;

    // Constructors

    public Vehicle() {
    }

    public Vehicle(String color, String make, String model, String year, int locationId) {
        this.color = color;
        this.make = make;
        this.model = model;
        this.year = year;
        this.locationId = locationId;
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

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
