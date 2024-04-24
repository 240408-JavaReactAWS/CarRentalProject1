import React, { useEffect, useState } from 'react'
import VehicleInfo from '../vehicles/VehicleInfo'
import { IOrder } from '../../models/IOrder'


function Order(props: number | IOrder) {

    const [order, setOrder] = useState<any>([]);

    useEffect(() => {

        let asyncCall = async () => {
            // Fetch may still need header fields
            let res = await fetch('http://localhost:8080/orders/' + props, {
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then((data) => data.json())
            .then((data) => setOrder(data))
            .catch((error) => {
                alert("There was an error loading order information")
                console.log(error)
            })
        }
    
        if (typeof props === 'number') {
            asyncCall()
        } else {
            setOrder(props)
        }

    },[])

    return (
        <>
            <div>
                <div>
                    <h1>Order Information</h1>
                    <p>Order Id: {order.orderId}</p>
                    <p>User Id: {order.user.userId}</p>
                    <p>Date and Time: {order.dateAndTime}</p>
                </div>
                <div>
                    <VehicleInfo {...order.vehicle}/>
                    {/* Button Component */}
                </div>
            </div>
        </>
    )


}