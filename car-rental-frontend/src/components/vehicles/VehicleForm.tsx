import React, {useEffect, useState} from "react";
import { ILocation } from "../../models/ILocation";
import axios from 'axios';

interface Vehicle {
    make: string,
    model: string,
    year: string,
    color: string,
    location?: ILocation
}

const VehicleForm = () => {

    const [currentLocation, setCurrentLocation] = useState<number>();
    const [locations, setLocations] = useState<ILocation[]>([]);
    const [vehicle, setVehicle] = useState<Vehicle>({
        make: '',
        model: '',
        year: '',
        color: ''
    });

    useEffect(() => {
        const fetchLocations = async () => {
            try {
                const response = await axios.get('http://localhost:8080/locations')
                //console.log(response.data);
                setLocations(response.data);
                setCurrentLocation(response.data[0].locationId)
            } catch (e) {
                console.log(e)
            }
        }

        fetchLocations();
    }, []);

    /*
    * This function handles the change of the vehicle input
    */

    const handleChangeVehicle = (e: React.ChangeEvent<HTMLInputElement>) => {
        setVehicle({...vehicle, [e.target.name]: e.target.value});
        //console.log(e.target.value);
    }

    /*
    * This function handles the change of the location select input
    */
    const handleChangeLocation = (e: React.ChangeEvent<HTMLSelectElement>) => {
      //setLocations({...locations, [e.target.name]: e.target.value});
        setCurrentLocation(parseInt(e.target.value));
    }

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        //console.log(vehicle);
        //console.log(`http://localhost:8080/vehicles/${currentLocation}/add`);
        try {
            axios.post(`http://localhost:8080/vehicles/${currentLocation}/add`, vehicle, {withCredentials: true});
        } catch (e) {
            console.log(e)
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <label htmlFor="make">Make</label>
            <input type="text" id="make" name="make" placeholder="Make" value={vehicle.make} onChange={handleChangeVehicle} />
            <label htmlFor="model">Model</label>
            <input type="text" id="model" name="model" placeholder="Model" value={vehicle.model} onChange={handleChangeVehicle} />
            <label htmlFor="year">Year</label>
            <input type="text" id="year" name="year" placeholder="Year" value={vehicle.year} onChange={handleChangeVehicle} />
            <label htmlFor="color">Color</label>
            <input type="text" id="color" name="color" placeholder="Color" value={vehicle.color} onChange={handleChangeVehicle} />
            <label htmlFor="location">Location</label>
            <select name="location" id="location" onChange={handleChangeLocation}>
                {locations.map(location => (
                    <option key={`loc-${location.locationId}`} value={location.locationId}>{`${location.streetAddress}, ${location.city} ${location.state}`}</option>
                ))};
            </select>
            <div className='ButtonDiv'>
                <button type="submit">Submit</button>
            </div>
        </form>
    )
}

export default VehicleForm;