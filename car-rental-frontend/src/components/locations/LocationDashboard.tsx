import React, { useEffect } from "react";
import LocationForm from "./LocationForm";
import LocationList from "./LocationList";
import { commonFunctions } from "../../common-functions";

const LocationDashboard = () => {

    useEffect(() => {
        let admin = commonFunctions.isAdmin();
    }, []);

    return (
        <div>
            <h1>Location Dashboard</h1>
                <h2>Add Location Form</h2>
                    <LocationForm />
                <h2>Location List</h2>
                    <LocationList />
        </div>
    )
}


export default LocationDashboard;