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
            <h1 className="contentHeading">Location Dashboard</h1>
            <div className='contentBody'>

            <div className='contentBlock'>
                <h2>Add Location Form</h2>
                    <LocationForm setLocations={setLocations} />
            </div>
            <div className='contentBlock'>
                <h2>Location List</h2>
                    <LocationList {...locations} />
            </div>
            </div>
        </div>
    )
}


export default LocationDashboard;