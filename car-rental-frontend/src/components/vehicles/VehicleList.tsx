import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { ILocation } from '../../models/ILocation';
import { IVehicle } from '../../models/IVehicle';

interface Vehicle {
    id: number,
    make: string,
    model: string,
    year: string,
    color: string,
    location?: ILocation
}

const VehicleList = () => {

    const [locations, setLocations] = useState<ILocation[]>([]);

    useEffect(() => {
        const fetchVehicles = async () => {
            try {
                const response = await axios.get('http://localhost:8080/locations');
                setLocations(response.data);
                console.log(response.data);
            } catch (e) {
                console.log(e);
            }
        }

        fetchVehicles();
    }, []);

    //  This function will handle the deletion of a vehicle from the database.
    //  The function will take in the vehicle id and the location of the vehicle.

    const handleDeleteVehicle = async (vId: number, selectedLocation: ILocation) => {
        try {
            //  Filter out the vehicle that is being deleted from the selected location.
            const updatedStock = selectedLocation.vehicleStock.filter((vehicle: IVehicle) => vehicle.id !== vId);
            //  Update the selected location with the new vehicle stock.
            const updatedLocation = {...selectedLocation, vehicleStock: updatedStock};
            //  Update the locations array with the updated location.
            setLocations(locations.map(location => location.locationId === selectedLocation.locationId ? updatedLocation : location));
            await axios.delete(`http://localhost:8080/vehicles/remove/${vId}`);
        } catch (e) {
            console.log(e);
        }

    }

    return (
        <>
            {locations.map(location => {
                return (
                    <div key={`loc-${location.locationId}`}>
                        {location.vehicleStock.map((vehicle: IVehicle) => (
                            <div key={`veh-${vehicle.id}`}>
                                <h3>Vehicle Information</h3>
                                <p>Vehicle Color: {vehicle.color}</p>
                                <p>Vehicle Make: {vehicle.make}</p>
                                <p>Vehicle Model: {vehicle.model}</p>
                                <p>Vehicle Year: {vehicle.year}</p>
                                <button onClick={() => handleDeleteVehicle(vehicle.id, location)}>Delete</button>
                                <h2>Location: {`${location.streetAddress} ${location.city} ${location.state}`} </h2>
                            </div>
                        ))}
                        
                    </div>
                )}
            )}
        </>
    )

}

export default VehicleList;