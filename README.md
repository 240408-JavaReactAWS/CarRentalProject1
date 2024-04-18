# Project 1 - Car Rental Application

* Assigned Date: 4/18/2024
* Due Date: 5/1/2024

## Technical Requirements:
Application must leverage the following full stack:
- PostgreSQL Database for persistence 
- API built with Java 8+ and Spring Boot 2+
- UI built with React with TypeScript
- Have proper documentation for UI/API
    - ie: a READ.ME that describes the following
        - what application is about
        - what technologies are required for the application
        - how to run the application
        - any relevant information for a new team to pick up the project

Technology framework requirements: 
- Java API will leverage the Spring Framework 
- Java API will use Spring Data JPA to communicate with the DB
- Java API will be RESTful (though HttpSession will be permitted)


Other requirements: 
- Application will demonstrate at least ten individual user stories 
- Application's own data model must be sufficiently complex (i.e. >2 related tables) 

Suggested stretch goals (not limited to):
- Secure your Java API endpoints using JWTs
- Dark mode
- Reset Password
- Notifications
- Leveraging a 3rd party API

## Presentations

Presentations will occur on the morning of May 1st 2024. All team members must have a speaking role in the presentation of the application, and a PowerPoint slideshow must accompany your presentation.

## Structure

    Tables
        Vehicles
        Users
        Locations
        Orders
    
    Vehicles
        Id
        Make
        Model
        Color
        Location FOREIGN KEY
        isAvaliable

    Users
        Id
        username
        password
        currentCar Optional
        allOrders Order[]
        Role
    
    OneToMany with Vehicles
    Locations
        Id
        streetAddress
        City
        State
        Vehicles[]

    Orders
        OrderId
        Time/Date
        VehicleId FOREIGN KEY
        UserId FOREIGN KEY
        isApproved
        isCompleted


## User Stories

    - Users and Admins can create accoutns
    - As a user, I can place rental orders
    - As a user, I can see my current order
    - As a user, I can see my past rentals
    - As a user, I can see all cars at a location
    - as a user, I can cancel orders
    = as a user, I can pick up and return ordered vehicles
    - As an admin, I can add, remove, and update vehicles to the database
    - As an admin, I can add, remove, and update locations to the database
    - As an admin I can see current and past orders 
    - as an Admin I can accept or reject orders
    - As an admin, I can transfer cars between locations

## Process

1. User places an order -> creates an order in table, adds order to their order list, isApproved and isCompleted is false
2. Admin reviews the order -> isApproved is true, if not approved, delete order from DB and users order list
3. Time comes, user picks up -> if(isApproved) vehicle isAvaliable = false, user current vehicle is updated
4. User done, returns car -> vehicle isAvaliable = true, Order isCompleted = true

## Assumptions

1. Orders begin and end at same location
2. Users only have 1 active order at a time (if a new order is made while 1 is there, reject new)