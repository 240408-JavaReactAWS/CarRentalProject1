import React, { useState } from "react";
import { ILocation } from "../../models/ILocation";
import axios from "axios";

interface ILocationUpdateFormProps {
    location: ILocation,
    setLocations: Function
}

const UpdateLocationForm = (props: ILocationUpdateFormProps) => {

    const [location, setLocation] = useState<ILocation>(props.location);
    const [editShow, setEditShow] = useState<boolean>(true);
    const [newLocation, setNewLocation] = useState<ILocation>()

    const handleChangeLocation = (e: React.ChangeEvent<HTMLInputElement>) => {
        setLocation({...location, [e.target.name]: e.target.value});
    }

    const handleShowUpdateForm = () => {
        setEditShow(!editShow);
    }

    const handleUpdateLocation = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            const response = await axios.put(`http://localhost:8080/locations/update/${location.locationId}`, location, {withCredentials: true})
            .then(response => {
                props.setLocations([])});
           // setNewLocation(response.data);
        } catch (e) {
            console.log(e);
        }
    }

    return (
        <>
            <div>
                <div className='ButtonDiv ButtonDash'>
                    <button onClick={() => handleShowUpdateForm()}>Update</button>
                </div>
                {!editShow &&
                <form onSubmit={handleUpdateLocation}>
                    <label>Street Address:</label>
                    <input type="text" name="streetAddress" value={location.streetAddress} onChange={handleChangeLocation} />
                    <label>City:</label>
                    <input type="text" name="city" value={location.city} onChange={handleChangeLocation} />
                    <label>State:</label>
                    <input type="text" name="state" value={location.state} onChange={handleChangeLocation} />
                    <label>Postal Code:</label>
                    <input type="text" name="postalCode" value={location.postalCode} onChange={handleChangeLocation} />
                    <div className='ButtonDiv ButtonDash'>
                        <button type="submit">Update Location</button>
                    </div>
                </form>
                }
            </div>
        </>
    )

}

export default UpdateLocationForm;