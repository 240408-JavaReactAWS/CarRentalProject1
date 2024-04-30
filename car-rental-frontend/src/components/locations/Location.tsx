import React, { useEffect, useState } from 'react'
import { ILocation } from '../../models/ILocation'
import axios from 'axios'
import Vehicle from '../vehicles/Vehicle'
import { IOrder } from '../../models/IOrder';
import { useNavigate } from 'react-router-dom';
import { commonFunctions } from '../../common-functions';


function Location(props: ILocation) {

  let currentOrder: IOrder | null;
  const navigate = useNavigate();
  const [hasOrder, hasOrderHandler] = useState<boolean>(false);
  const [adminStatus, setAdminStatus] = useState<boolean>(false);

  let placeOrder = async (vehicleId: number) => { 
    let res = await axios.post('http://localhost:8080/orders/placeorder/' + vehicleId, {}, { withCredentials: true })
    .then((data) => console.log(data))
    .catch((error) => {
      alert("There was an error")
      console.log(error)
    })
    navigate('/orders')
  }

  useEffect(() => {

    let asyncCall = async () => {
        let isValidSession = await commonFunctions.validateSession();
        let isAdmin = await commonFunctions.isAdmin();
        setAdminStatus(isAdmin)
        if (!isValidSession) {
            // Redirect to login
            navigate('/login')
        } else if (isAdmin) {

        } else {

        }
      let res = await axios.get('http://localhost:8080/orders/current', { withCredentials: true })
        .then((response) => {
          currentOrder = response.data
          hasOrderHandler(true)
        })
        .catch((error) => {
          currentOrder = null;
          hasOrderHandler(false)
        })
    }
    asyncCall()

},[])

  return (
    <div>
      <h1>{props.streetAddress}, {props.city}, {props.state} {props.postalCode}</h1>
        {props.vehicleStock.map((vehicle) => {
            if(vehicle.isAvailable == true) {
                return (
                <div>
                  <p>Vehicle Id: {vehicle.id}</p>
                  <p>Vehicle Color: {vehicle.color}</p>
                  <p>Vehicle Make: {vehicle.make}</p>
                  <p>Vehicle Model: {vehicle.model}</p>
                  <p>Vehicle Year: {vehicle.year}</p>
                  {!hasOrder && !adminStatus ? <button onClick={() => placeOrder(vehicle.id)}>Order</button> : null}
                </div>
            )}
        })}
    </div>
  )
}

export default Location
