# Project 1 - Car Rental Application

* Assigned Date: 4/18/2024
* Due Date: 5/1/2024
* GROUP
* Elijah Hedrick
* Devon Richey
* Tyler Emanuelle Morton
* Ihtheram Chowdhury

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

## Working

    The backend of the project written in Java using Spring is in the car-rental-backend folder.

    The frontend made with React is in the car-rental-frontend folder. After downloading the git repo, open a terminal, cd to car-rental-frontend, and run "npm i" to install all node_modules dependencies.

    When working in the command line make sure you are in the correct directory for your commands.

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

    - Users and Admins can create accounts                                      Eli             X
    - As a user, I can place rental orders                                      Eli             X
    - As a user, I can see my current order                                     Eli             X
    - As a user, I can see my past rentals                                      Eli             X
    - As a user, I can see all cars at a location                               Ihtheram        X
    - as a user, I can cancel orders                                            Ihtheram        
    - as a user, I can pick up and return ordered vehicles                      Ihtheram
    - As an admin, I can add, remove, and update vehicles to the database       Tyler
    - As an admin, I can add, remove, and update locations to the database      Tyler
    - As an admin I can see current and past orders                             Tyler           X
    - as an Admin I can accept or reject orders                                 Devon           X
    - As an admin, I can transfer cars between locations                        Devon           O    
    - Users and Admins can log in                                               Devon           X
    
## Process

1. User places an order -> creates an order in table, adds order to their order list, isApproved and isCompleted is false
2. Admin reviews the order -> isApproved is true, if not approved, mark isCompleted as true and leave isApproved as false to makr a refused order
3. Time comes, user picks up -> if(isApproved) vehicle isAvaliable = false, user current vehicle is updated
4. User done, returns car -> vehicle isAvaliable = true, Order isCompleted = true

## Assumptions

1. Orders begin and end at same location
2. Users only have 1 active order at a time (if a new order is made while 1 is there, reject new)