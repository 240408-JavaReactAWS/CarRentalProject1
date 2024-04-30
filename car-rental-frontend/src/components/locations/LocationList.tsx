import React, { useEffect, useState }  from "react";
import { ILocation } from "../../models/ILocation";    
import axios from "axios";
import UpdateLocationForm from "./UpdateLocationForm";


const LocationList = (props: ILocation[]) => {
    
    const [locations, setLocations] = useState<ILocation[]>([]);

    useEffect(() => {
        const getLocations = async () => {
            try {
                const response = await axios.get('http://localhost:8080/locations', {withCredentials: true});
                setLocations(response.data);
            } catch (e) {
                console.log(e);
            }
        }

        getLocations();
    },[props, locations]);

    const handleDeleteLocation = async (locationId: number) => {
        try {
            const updatedLocations = locations.filter(location => location.locationId !== locationId);
            setLocations(updatedLocations);
            await axios.delete(`http://localhost:8080/locations/remove/${locationId}`, {withCredentials: true});
        } catch (e) {
            console.log(e);
        }
    
    }

    return (
        <>
            {locations.map(location => {
                return (
                <div key={`locat-${location.locationId}`}>
                    <h3>{location.streetAddress}</h3>
                    <p>{`${location.city}, ${location.state} ${location.postalCode}`}</p>
                    <h4>Vehicle Stock</h4>
                    <p>{location.vehicleStock.map(vehicle => ( <>
                        <p key={`veh-${vehicle.id}`}>{`${vehicle.make} ${vehicle.model} ${vehicle.year} ${vehicle.color}`}</p>
                    </>))}</p>
                    <div className="ButtonDiv">
                        <button className="delete" onClick={() => handleDeleteLocation(location.locationId)}>Delete</button>
                    </div>
                    <UpdateLocationForm location={location} setLocations={setLocations}/>
                </div>)
            })
            }
        </>
    )

}


export default LocationList;