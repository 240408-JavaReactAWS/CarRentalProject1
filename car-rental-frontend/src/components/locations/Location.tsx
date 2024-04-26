import React, { useEffect } from 'react'
import { ILocation } from '../../models/ILocation'
import axios from 'axios'


function Location(props: ILocation) {

  return (
    <div>
      <h1>{props.streetAddress}, {props.city}, {props.state} {props.postalCode}</h1>
      <ul>
        {props.vehicleStock.map((vehicle) => {
            if(vehicle.isAvailable == true) {
                return (
                <li key={"location"+props.locationId+"vehicle"+vehicle.id}>{vehicle.color} {vehicle.year} {vehicle.make} {vehicle.model}</li>
            )}
        })}
      </ul>
    </div>
  )
}

export default Location
