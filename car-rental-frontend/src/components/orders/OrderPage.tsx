import React, { SyntheticEvent, useEffect, useState } from 'react'
import { IUser } from '../../models/IUser';
import Order from './Order';
import { IOrder } from '../../models/IOrder';
import { IOrderDTO } from '../../models/IOrderDTO';
import OrderNav from './OrderNav';
import './OrderPage.css';
import axios from 'axios';



function OrderPage() {
    
    //console.log(props)
    //console.log(localStorage.user)
    let user = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user') || '{}') : {} as IUser

    const [orderList, setOrderList] = useState<any>([])
    const [currentOrder, setCurrentOrder] = useState<any>(null)

    // GETs All Orders. 
    let asyncCallAllOrders = async () => {
        console.log("Getting all orders")
        // Check headers
        let res = await fetch('http://localhost:8080/orders/allorders', {
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then((data) => data.json())
        .then((data) => {
            // console.log(data)
            setOrderList(data)})
        .catch((error) => {
            // alert("There was an error loading order list")
            console.log(error)
        })
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
        console.log("Getting pending orders")
        // Check headers
        let res = await fetch('http://localhost:8080/orders/pendingorders', {
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then((data) => data.json())
        .then((data) => setOrderList(data))
        .catch((error) => {
            // alert("There was an error loading order list")
            console.log(error)
        })
    }
    

    // GETs All Completed Orders. Modify endpoint once defined
    let asyncCallCompletedOrders = async () => {
        console.log("Getting completed orders")
        // Check headers
        let res = await fetch('http://localhost:8080/orders/completedorders', {
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then((data) => data.json())
        .then((data) => setOrderList(data))
        .catch((error) => {
            // alert("There was an error loading order list")
            console.log(error)
        })
    }

    // GETs All User Orders.
    let asyncCallUserOrders = async () => {
        // Check headers
        let res = await fetch('http://localhost:8080/orders/myorders', {
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then((data) => data.json())
        .then((data) => setOrderList(data))
        .catch((error) => {
            // alert("There was an error loading order list")
            console.log(error)
        })
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
            let res = await axios.get('http://localhost:8080/orders/current')
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

        if (user.isAdmin) {
            asyncCallAllOrders()
        } else {
            asyncCallUserOrders()
            asyncCallCurrentUserOrder()
        }
    
    },[])
        

    //console.log(orderList)
    console.log(currentOrder)

    return (
        <>
            <h1 className='contentHeading'>Orders</h1>
            <div className='contentBody'>
                {
                    (user.isAdmin) && (
                        <OrderNav asyncCallAllOrders={asyncCallAllOrders} asyncCallPendingOrders={asyncCallPendingOrders} asyncCallCompletedOrders={asyncCallCompletedOrders}
                        //filterOrders={filterOrders}
                        />
                    )
                }
                {
                    (!user.isAdmin && currentOrder !== null) && (
                        <div style={{ backgroundColor: 'grey' }} className='currentOrder'>
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