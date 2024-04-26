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

    const handleDeleteVehicle = async (id: number) => {
        try {
            //await axios.delete(`http://localhost:8080/vehicles/remove/${id}`);
            //console.log(locations);
            //for (let location of locations) {
                //location.vehicleStock.filter((vehicle: Vehicle) => {
                    //vehicle.id != id})};
            console.log(id);
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
                                <button onClick={() => handleDeleteVehicle(vehicle.id)}>Delete</button>
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