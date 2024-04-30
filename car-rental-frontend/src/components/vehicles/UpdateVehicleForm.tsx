import React, { useState, useEffect, SyntheticEvent } from 'react';
import axios from 'axios';
import { ILocation } from '../../models/ILocation';
import { IVehicle } from '../../models/IVehicle';

interface IVehicleUpdateFormProps {
    vehicle: IVehicle,
    selectedLocation: ILocation,
    locations: ILocation[],
}


const UpdateVehicleForm = (props: IVehicleUpdateFormProps) => {

    const [locations, setLocations] = useState<ILocation[]>(props.locations);
    const [vehicle, setVehicle] = useState<IVehicle>(props.vehicle);
    const [selectedLocation, setSelectedLocation] = useState<ILocation>(props.selectedLocation);
    const [editShow, setEditShow] = useState<boolean>(true);


    const handleUpdateVehicle = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            const vId = vehicle.id;
            //update the vehicle object with the selected location object
            const updatedVehicle = { ...vehicle, location: selectedLocation };
            //update the vehicle object in the state
            setVehicle(updatedVehicle);
            //update the vehicle object in the database
            await axios.put(`http://localhost:8080/vehicles/update/${vId}`, updatedVehicle, { withCredentials: true });
        } catch (e) {
            console.log(e);
        }

    }

    const handleChangeVehicle = (e: React.ChangeEvent<HTMLInputElement>) => {
        setVehicle({ ...vehicle, [e.target.name]: e.target.value });
    }


    const handleChangeLocation = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedLocation(JSON.parse(e.target.value));
    }

    const handleShowUpdateForm = () => {
        setEditShow(!editShow);

    }

    return (
        <>
            <div>
                <div className='ButtonDiv'>
                    <button onClick={() => handleShowUpdateForm()}>Update</button>
                </div>


                {!editShow &&
                    <form onSubmit={handleUpdateVehicle}>

                        <label htmlFor="make">Make: </label>
                        <input type="text" id="make" name="make" value={vehicle.make} onChange={handleChangeVehicle} /><br />

                        <label htmlFor="model">Model: </label>
                        <input type="text" id="model" name="model" value={vehicle.model} onChange={handleChangeVehicle} /><br />

                        <label htmlFor="year">Year: </label>
                        <input type="text" id="year" name="year" value={vehicle.year} onChange={handleChangeVehicle} /><br />

                        <label htmlFor='color'>Color:</label>
                        <input type="text" id='color' name="color" value={vehicle.color} onChange={handleChangeVehicle} /><br />

                        <label htmlFor='location'>Location:</label>
                        <select id='location' value={JSON.stringify(selectedLocation)} onChange={handleChangeLocation}><br />
                            {locations.map((location) => <option key={`loca-${location.locationId}`} value={JSON.stringify(location)}>{`${location.streetAddress}, ${location.city} ${location.state}`}</option>)}
                        </select>

                        <div className='ButtonDiv'>
                            <button type="submit">Update Vehicle</button>
                        </div>
                    </form>}
            </div>
        </>

    )

}


export default UpdateVehicleForm;