import React, {useState, useEffect, SyntheticEvent}from 'react';
import axios from 'axios';
import { ILocation } from '../../models/ILocation';
import { IVehicle } from '../../models/IVehicle';

interface IVehicleUpdateFormProps {
    vehicle: IVehicle,
    selectedLocation: ILocation,
    locations: ILocation[]

}


const UpdateVehicleForm = (props: IVehicleUpdateFormProps) => {

    const [locations, setLocations] = useState<ILocation[]>(props.locations);
    const [vehicle, setVehicle] = useState<IVehicle>(props.vehicle);
    const [selectedLocation, setSelectedLocation] = useState<ILocation>(props.selectedLocation);

    const handleUpdateVehicle = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            const vId = vehicle.id;
        //    const updatedStock = selectedLocation.vehicleStock.map((instanceVehicle: IVehicle) => vehicle.id === vId ? vehicle : instanceVehicle);
            const updatedVehicle = {...vehicle, location: selectedLocation};
            setVehicle(updatedVehicle);
        //    setLocations(locations.map(location => location.locationId === selectedLocation.locationId ? updatedLocation : location));
            await axios.put(`http://localhost:8080/vehicles/update/${vId}`, updatedVehicle, {withCredentials: true});
        } catch (e) {
            console.log(e);
        }

    }

    const handleChangeVehicle = (e: React.ChangeEvent<HTMLInputElement>) => {
        setVehicle({...vehicle, [e.target.name]: e.target.value});
        //console.log(e.target.value);
    }


    const handleChangeLocation = (e: React.ChangeEvent<HTMLSelectElement>) => {
        //setLocations({...locations, [e.target.name]: e.target.value});
        setSelectedLocation(JSON.parse(e.target.value));
    }
  

    return (
        <>
            <form onSubmit={handleUpdateVehicle}>
                <label>Make:</label>
                <input type="text" name="make" value={vehicle.make} onChange={handleChangeVehicle} />
                <label>Model:</label>
                <input type="text" name="model" value={vehicle.model} onChange={handleChangeVehicle} />
                <label>Year:</label>
                <input type="text" name="year" value={vehicle.year} onChange={handleChangeVehicle} />
                <label>Color:</label>
                <input type="text" name="color" value={vehicle.color} onChange={handleChangeVehicle} />
                <label>Location:</label>
                <select value={JSON.stringify(selectedLocation)} onChange={handleChangeLocation}>
                    {locations.map((location) => <option key={`loca-${location.locationId}`} value={JSON.stringify(location)}>{`${location.streetAddress}, ${location.city} ${location.state}`}</option>)}
                </select>                 
                <button type="submit">Update Vehicle</button>
            </form>
        </>

    )

}


export default UpdateVehicleForm;