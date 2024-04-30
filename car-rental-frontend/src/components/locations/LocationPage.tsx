import React, { useEffect, useState } from 'react'
import axios from 'axios'
import { ILocation } from '../../models/ILocation'
import Location from './Location'

function LocationPage() {

    const [locations, setLocations] = useState<ILocation[]>([])
    const [selectedLocation, setSelectedLocation] = useState<ILocation | null>(null)

    useEffect(() => {
        let asyncCall = async () => {
            let res = await axios.get('http://localhost:8080/locations')
            .then((response) => {
                setLocations(response.data)

            })
            .catch((error) => {
                console.log(error)
            })
        }
        asyncCall()
    }, [])

    const setSelectedLocationHandler = (e: React.ChangeEvent<HTMLSelectElement>) => {
        if(parseInt(e.target.value) === 0) {
            setSelectedLocation(null)
            return;
        }
        let location: ILocation = locations.find(location => location.locationId == parseInt(e.target.value)) as ILocation
        setSelectedLocation(location)
    }


    return (
        <>
            <h1 className='contentHeading'>Locations</h1>
            <div className='contentBody'>
                <select name="locations" onChange={setSelectedLocationHandler} title="Select a location">
                    <option value="0">Select a location</option>
                    {locations.map((location) => {
                        return (
                            <option value={location.locationId} key={"location" + location.locationId}>{location.streetAddress}, {location.city}, {location.state} {location.postalCode}</option>
                        )
                    })}
                </select>
                {(selectedLocation != null) ? <Location {...selectedLocation} /> : null}
            </div>
        </>
    )
}

export default LocationPage
