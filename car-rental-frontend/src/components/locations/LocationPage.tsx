import React, { useEffect, useState } from 'react'
import axios from 'axios'
import { ILocation } from '../../models/ILocation'

function LocationPage() {

    const [locations, setLocations] = useState<ILocation[]>([])
    const [selectedLocation, setSelectedLocation] = useState<ILocation | null | undefined>(null)

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
        let location: ILocation | undefined = locations.find(location => location.locationId == parseInt(e.target.value))
        setSelectedLocation(location)
    }


  return (
    <div>
        <h1>Locations</h1>
        <select name="locations" onChange={setSelectedLocationHandler}>
            <option value="0">Select a location</option>
            {locations.map((location) => {
                return (
                    <option value={location.locationId} key={"location"+location.locationId}>{location.streetAddress}, {location.city}, {location.state} {location.postalCode}</option>
                )
            })}
        </select>
        <h1>Selected location ID: {selectedLocation?.locationId}</h1>
        <h1>Selected location: {selectedLocation?.streetAddress}</h1>
    </div>
  )
}

export default LocationPage
