import React, { useEffect, useState, useRef } from 'react'
import VehicleInfo from '../vehicles/VehicleInfo'
import Button from '../Button'
import { IOrder } from '../../models/IOrder'
import { IOrderDTO } from '../../models/IOrderDTO'
import {Source, IButtonProps} from '../../models/IButtonProps'
import { commonFunctions } from '../../common-functions';
import axios from 'axios'
import './Order.css'


function Order(props: IOrderDTO) {

    //console.log(props)

    const [order, setOrder] = useState<any>(props.order);
    const [approvalMessage, setApprovalMessage] = useState<string>("Pending")
    let [isAdmin, setIsAdmin] = useState<boolean>(false)
    let [hasCar, setHasCar] = useState<boolean>(false)



    useRef(() => {
        let asyncCall = async () => {
            isAdmin = await commonFunctions.isAdmin();
            setIsAdmin(isAdmin);
        }
        asyncCall();
     })

    useState(() => {
        let asyncCall = async () => {
            hasCar = await commonFunctions.hasCar();
            setHasCar(hasCar);
        }
        asyncCall();
    })  


    let approveReject = (approval: boolean) => {
        
        let asyncCall = async () => {
            let res = await axios.patch('http://localhost:8080/orders/' + order.orderId + "?approvalStatus=" + approval, {}, { withCredentials: true })
            //.then((data) => console.log(data))
            .then((response) => {
                setOrder(response.data)
                setApprovalMessage(approval ? "Approved" : "Rejected")
            })
            .catch((error) => {
              alert("There was an error")
              console.log(error)
            })
            /*let res = await fetch('http://localhost:8080/orders/' + order.orderId, { 
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
            })*/
        }

        asyncCall()
    }

    let cancelOrder = () => {
        
        let asyncCall = async () => {
            let res = await axios.delete('http://localhost:8080/orders/current', { withCredentials: true })
            .then((response) => {
                console.log(response.data, response.status, "Order Cancelled")
            })
            .catch((error) => {
              alert("There was an error cancelling the order")
              console.log(error)
            })
        }
        asyncCall()
    }

    let pickupOrder = () => {
        let asyncCall = async () => {
            let res = await axios.patch('http://localhost:8080/vehicles/pickup', { withCredentials: true })
            .then(() => {
                setApprovalMessage("Picked Up")
            })
            .catch((error) => {
              alert("There was an error picking up the vehicle")
              console.log(error)
            })
        }
        asyncCall()
    }

    let returnOrder = () => {
        let asyncCall = async () => {
            let res = await axios.patch('http://localhost:8080/vehicles/return', { withCredentials: true })
            .then(() => {
                setApprovalMessage("Returned")
            })
            .catch((error) => {
              alert("There was an error returning the vehicle")
              console.log(error)
            })
        }
        asyncCall()
    }


    useEffect(() => {
        if (order.isCompleted) {
            if (order.isApproved) {
                setApprovalMessage("Completed")
            } else {
                setApprovalMessage("Rejected")
            }
        } else if(order.isApproved) {
            setApprovalMessage("Approved")
        } else {
            setApprovalMessage("Pending")
        }
    }, [])

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

    return (
        <>
            <div className='orderBlock'>
            
                <div>
                    <h2>Order Information</h2>
                    <p>Order Id: {order.orderId}</p>
                    <p>User Id: {props.userId}</p>
                    <p>Date and Time: {order.dateAndTime}</p>
                    <p>Approved: {approvalMessage}</p>
                </div>
                <div>
                    <VehicleInfo {...order.vehicle}/>
                    {order.isApproved == false && order.isCompleted == false ? <Button 
                        source={Source.Order} 
                        sourceId={order.orderId} 
                        shouldDisplay={!order.isCompleted} 
                        methods={isAdmin?{approveReject: approveReject}
                        :!hasCar?{cancelOrder:cancelOrder, pickUpOrder:pickupOrder}
                        :{returnOrder:returnOrder} }/> : <></>}
                </div>
            </div>
        </>
    )
}


export default Order