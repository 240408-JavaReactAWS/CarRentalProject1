import React, { useEffect, useState } from 'react'
import VehicleInfo from '../vehicles/VehicleInfo'
import Button from '../Button'
import { IOrder } from '../../models/IOrder'
import { IOrderDTO } from '../../models/IOrderDTO'
import {Source, IButtonProps} from '../../models/IButtonProps'


function Order(props: IOrderDTO) {

    //console.log(props)

    const [order, setOrder] = useState<any>(props.order);
    
    let approveReject = (approval: boolean) => {
        
        let asyncCall = async () => {
            let res = await fetch('http://localhost:8080/orders/myOrder' + order.orderId, { 
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(approval)
            })
            .then((data) => data.json())
            .then((data) => setOrder(data))
            .catch((error) => {
                alert("There was an error")
                console.log(error)
            })
        }

        asyncCall()

    }

    /*
    useEffect(() => {

        let asyncCall = async () => {
            // Fetch may still need header fields
            let res = await fetch('http://localhost:8080/orders/' + props, {
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then((data) => data.json())
            .then((data) => {
                console.log(data)
                setOrder(data)})
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
    */

    // console.log(order.isCompleted)
    // console.log(order.vehicle)
    // console.log(order)

    let approvalMessage: string
    if (order.isCompleted) {
        if (order.isApproved) {
            approvalMessage = "Approved"
        } else {
            approvalMessage = "Rejected"
        }
    } else {
        approvalMessage = "Pending"
    }

    return (
        <>
            <div>
                <div>
                    <h1>Order Information</h1>
                    <p>Order Id: {order.orderId}</p>
                    <p>User Id: {props.userId}</p>
                    <p>Date and Time: {order.dateAndTime}</p>
                    <p>Approved: {approvalMessage}</p>
                </div>
                <div>
                    <VehicleInfo {...order.vehicle}/>
                    <Button 
                        source={Source.Order} 
                        sourceId={order.orderId} 
                        shouldDisplay={!order.isCompleted} 
                        methods={{approveReject: approveReject}}
                    />
                </div>
            </div>
        </>
    )


}

export default Order