import React, { useEffect, useState } from "react";
import LocationForm from "./LocationForm";
import LocationList from "./LocationList";
import { commonFunctions } from "../../common-functions";
import { ILocation } from "../../models/ILocation";
import { useNavigate } from "react-router-dom";

const LocationDashboard = () => {

    const navigate = useNavigate();

    const [locations, setLocations] = useState<ILocation[]>([]);

    useEffect(() => {
        const checkAdmin = async () => {
            let admin = await commonFunctions.isAdmin();
            if (!admin) {
                navigate("/");
            }
        }
        checkAdmin();
    },[]);

    return (
        <div>
            <h1>Location Dashboard</h1>
                <h2>Add Location Form</h2>
                    <LocationForm setLocations={setLocations} />
                <h2>Location List</h2>
                    <LocationList {...locations} />
        </div>
    )
}


export default LocationDashboard;