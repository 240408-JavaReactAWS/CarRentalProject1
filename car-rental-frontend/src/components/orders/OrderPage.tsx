import React, { SyntheticEvent, useEffect, useState } from 'react'
import { IUser } from '../../models/IUser';
import Order from './Order';
import { IOrder } from '../../models/IOrder';
import { IOrderDTO } from '../../models/IOrderDTO';
import OrderNav from './OrderNav';
import './OrderPage.css';
import axios from 'axios';
import { commonFunctions } from '../../common-functions';
import { redirect, useNavigate } from 'react-router-dom';



function OrderPage() {
    
    //console.log(props)
    //console.log(localStorage.user)

    const [adminStatus, setAdminStatus] = useState<boolean>(false)
    const [orderList, setOrderList] = useState<any>([])
    const [currentOrder, setCurrentOrder] = useState<any>(null)

    const navigate = useNavigate()

    // GETs All Orders. 
    let asyncCallAllOrders = async () => {
        try {
            let res = await axios.get('http://localhost:8080/orders/allorders', { withCredentials: true })
            if (res.status != 200) {
                throw new Error("Error: " + res.status)
            } else {
                setOrderList(res.data.sort((a: IOrderDTO, b: IOrderDTO) => {
                    return b.order.orderId - a.order.orderId
                }))
            }
        } catch (error: any) {
            let status = error.response.status
            if (status === 404) {
                console.log("No orders")
                setCurrentOrder(null)
            } else {
                console.log("Error: " + error)
            }
        }
    }

    /*
    // Filter All Orders by isApproved
    let filterOrders = (e: React.MouseEvent<HTMLButtonElement>) => {
        asyncCallAllOrders()

        console.log((e.target as HTMLButtonElement).value)

        let filteredOrders = orderList.filter((orderDTO: IOrderDTO) => {
            if ((e.target as HTMLButtonElement).value == "all") {
                return orderDTO;
            } else if ((e.target as HTMLButtonElement).value == "pending") {
                return orderDTO.order.isCompleted == false;
            } else if ((e.target as HTMLButtonElement).value == "completed") {
                return orderDTO.order.isCompleted == true;                
            }
        })
        setOrderList(filteredOrders)
    }
    */

    // GETs All Pending Orders. Modify endpoint once defined
    let asyncCallPendingOrders = async () => {
        try {
            let res = await axios.get('http://localhost:8080/orders/pendingorders', { withCredentials: true })
            if (res.status != 200) {
                throw new Error("Error: " + res.status)
            } else {
                setOrderList(res.data.sort((a: IOrderDTO, b: IOrderDTO) => {
                    return b.order.orderId - a.order.orderId
                }))
            }
        } catch (error: any) {
            let status = error.response.status
            if (status === 404) {
                console.log("No pending orders")
                setCurrentOrder(null)
            } else {
                console.log("Error: " + error)
            }
        }
    }
    

    // GETs All Completed Orders. Modify endpoint once defined
    let asyncCallCompletedOrders = async () => {
        try {
            let res = await axios.get('http://localhost:8080/orders/completedorders', { withCredentials: true })
            if (res.status != 200) {
                throw new Error("Error: " + res.status)
            } else {
                setOrderList(res.data.sort((a: IOrderDTO, b: IOrderDTO) => {
                    return b.order.orderId - a.order.orderId
                }))
            }
        } catch (error: any) {
            let status = error.response.status
            if (status === 404) {
                console.log("No completed orders")
                setCurrentOrder(null)
            } else {
                console.log("Error: " + error)
            }
        }
    }

    // GETs All User Orders.
    let asyncCallUserOrders = async () => {
        try {
            let res = await axios.get('http://localhost:8080/orders/myorders',  { withCredentials: true }
            )
            if (res.status != 200) {
                throw new Error("Error: " + res.status)
            } else {
                res.data.pop()
                setOrderList(res.data.sort((a: IOrderDTO, b: IOrderDTO) => {
                    return b.order.orderId - a.order.orderId
                }))
            }
        } catch (error: any) {
            let status = error.response.status
            if (status === 404) {
                console.log("No orders")
                setCurrentOrder(null)
            } else {
                console.log("Error: " + error)
            }
        }
    }

    // GETs Current User Order. Modify endpoint once defined
    let asyncCallCurrentUserOrder = async () => {
        // Check headers
        /*
        let res = await fetch('http://localhost:8080/orders/' + user.username + '/current', {
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then((data) => data.json())
        .then((data) => {
            if (data)
        })
        .then((data) => {
            console.log("Data: " + data)
            currentOrder = data})
        .catch((error) => {
            console.log("Error: " + error)
        })
        */

        try {
            let res = await axios.get('http://localhost:8080/orders/current', { withCredentials: true })
            if (res.status != 200) {
                throw new Error("Error: " + res.status)
            }
            setCurrentOrder(res.data)
        } catch (error: any) {
            let status = error.response.status
            if (status === 404) {
                console.log("No current order")
                setCurrentOrder(null)
            } else {
                console.log("Error: " + error)
            }
        }

        
    }
    

    useEffect(() => {

        let asyncCall = async () => {
            let isValidSession = await commonFunctions.validateSession();
            let adminStatus = await commonFunctions.isAdmin();
            if (!isValidSession) {
                // Redirect to login
                navigate('/login', {state:{isLoggedIn: false}})
            } else if (adminStatus) {
                await asyncCallAllOrders()
            } else {
                await asyncCallUserOrders()
                await asyncCallCurrentUserOrder()
            }

            setAdminStatus(adminStatus)
        }

        /*
        if (user.isAdmin) {
            asyncCallAllOrders()
        } else {
            asyncCallUserOrders()
            asyncCallCurrentUserOrder()
        }
        */

        asyncCall()
    
    },[])
        

    //console.log(orderList)
    //console.log(currentOrder)

    return (
        <>
            <h1 className='contentHeading'>Orders</h1>
            <div className='contentBody'>
                {
                    (adminStatus) && (
                        <OrderNav asyncCallAllOrders={asyncCallAllOrders} asyncCallPendingOrders={asyncCallPendingOrders} asyncCallCompletedOrders={asyncCallCompletedOrders}
                        //filterOrders={filterOrders}
                        />
                    )
                }
                {
                    (!adminStatus && currentOrder !== null) && (
                        <div className='currentOrder'>
                            <Order {...currentOrder} />
                        </div>
                    )
                }
                {
                    orderList.map((orderDTO: IOrderDTO) => {
                        //console.log(orderDTO)
                        return (
                            <Order key={"order-block-" + orderDTO.order.orderId} {...orderDTO} />
                        )
                    }
                    )}
            </div>
        </>
    )

}

export default OrderPage