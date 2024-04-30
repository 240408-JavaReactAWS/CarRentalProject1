import React, { useEffect, useState } from "react";
import LocationForm from "./LocationForm";
import LocationList from "./LocationList";
import { commonFunctions } from "../../common-functions";
import { ILocation } from "../../models/ILocation";

const LocationDashboard = () => {

    useEffect(() => {
        let admin = commonFunctions.isAdmin();
    }, []);

    const [locations, setLocations] = useState<ILocation[]>([]);

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