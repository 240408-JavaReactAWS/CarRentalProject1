import React, { useEffect, useState } from 'react'
import { IVehicle } from '../../models/IVehicle';

function VehicleInfo(props: number | IVehicle) {
    // This component will display the vehicle information in a block format on both the vehicle and order pages.

    // The vehicle information will include the following:
    // - Vehicle Id
    // - Vehicle Make
    // - Vehicle Model
    // - Vehicle Year
    // - Vehicle Color

    // GOAL: Send an http request when this component shows up on the screen
    // The request should be a get request and should receive the information of a specific vehicle

    const [vehicle, setVehicle] = useState<any>(props);

    useEffect(() => {


        let asyncCall = async () => {
            // Fetch may still need header fields
            let res = await fetch('http://localhost:8080/vehicles/' + props, {
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then((data) => data.json())
            .then((data) => setVehicle(data))
            .catch((error) => {
                alert("There was an error loading vehicle information")
                console.log(error)
            })
        }

        if (typeof props === 'number') {
            asyncCall()
        } else {
            setVehicle(props)
        }
    
    }, [])

    return (
        <>
            <div>
                <h1>Vehicle Information</h1>
                <p>Vehicle Id: {vehicle.id}</p>
                <p>Vehicle Color: {vehicle.color}</p>
                <p>Vehicle Make: {vehicle.make}</p>
                <p>Vehicle Model: {vehicle.model}</p>
                <p>Vehicle Year: {vehicle.year}</p>
            </div>
        </>
    )
}

export default VehicleInfo