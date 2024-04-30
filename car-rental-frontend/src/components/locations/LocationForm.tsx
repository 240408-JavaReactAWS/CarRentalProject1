import React, { useState } from "react";
import { ILocation } from "../../models/ILocation";
import axios from "axios";
import { useNavigate } from "react-router-dom";

interface ILocationFormProps {
    setLocations: Function
}


const LocationForm = (props:ILocationFormProps) => {

    const navigate = useNavigate();

    const [location, setLocation] = useState<ILocation>({
        streetAddress: '',
        city: '',
        state: '',
        postalCode: '',
        locationId: 0,
        vehicleStock: []
    });

    const handleChangeLocation = (e: React.ChangeEvent<HTMLInputElement>) => {
        setLocation({...location, [e.target.name]: e.target.value});
    }

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            const response = await axios.post("http://localhost:8080/locations/add", location, {withCredentials: true}).then(response => {props.setLocations([])});
        } catch (e) {
            console.log(e);
        }
    }
    
    return (
        <div>
            <h3>Location Form</h3>
            <form onSubmit={handleSubmit}>
                <label htmlFor="streetAddress">Street Address</label>
                <input type="text" id="streetAddress" placeholder="Street Address" name="streetAddress" value={location.streetAddress} onChange={handleChangeLocation} />
                <label htmlFor="city">City</label>
                <input type="text" id="city" name="city" placeholder="City" value={location.city} onChange={handleChangeLocation}/>
                <label htmlFor="state">State</label>
                <input type="text" id="state" name="state" placeholder="State" value={location.state} onChange={handleChangeLocation}/>
                <label htmlFor="postalCode">Postal Code</label>
                <input type="text" id="postalCode" name="postalCode" placeholder="Postal Code" value={location.postalCode} onChange={handleChangeLocation}/>
                <div className='ButtonDiv'>
                    <button type="submit">Add Location</button>
                </div>
            </form>
        </div>
    )
}

export default LocationForm;