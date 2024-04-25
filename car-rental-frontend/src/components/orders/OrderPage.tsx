import React, { useEffect, useState } from 'react'
import { IUser } from '../../models/IUser';
import Order from './Order';
import { IOrder } from '../../models/IOrder';

function OrderPage(props: IUser) {
    
    const [orderList, setOrderList] = useState<any>([])
    const [currentOrder, setCurrentOrder] = useState<IOrder>({} as IOrder)

    // GETs All Orders. 
    let asyncCallAllOrders = async () => {
        // Check headers
        let res = await fetch('http://localhost:8080/orders/allorders', {
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then((data) => data.json())
        .then((data) => setOrderList(data))
        .catch((error) => {
            alert("There was an error loading order list")
            console.log(error)
        })
    }

    // GETs All Pending Orders. Modify endpoint once defined
    /*
    let asyncCallPendingOrders = async () => {
        // Check headers
        let res = await fetch('http://localhost:8080/orders/pendingorders', {
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then((data) => data.json())
        .then((data) => setOrderList(data))
        .catch((error) => {
            alert("There was an error loading order list")
            console.log(error)
        })
    }
    */

    // GETs All Completed Orders. Modify endpoint once defined
    /*
    let asyncCallCompletedOrders = async () => {
        // Check headers
        let res = await fetch('http://localhost:8080/orders/completedorders', {
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then((data) => data.json())
        .then((data) => setOrderList(data))
        .catch((error) => {
            alert("There was an error loading order list")
            console.log(error)
        })
    }
    */ 

    // GETs All User Orders.
    let asyncCallUserOrders = async () => {
        // Check headers
        let res = await fetch('http://localhost:8080/orders/' + props.username, {
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then((data) => data.json())
        .then((data) => setOrderList(data))
        .catch((error) => {
            alert("There was an error loading order list")
            console.log(error)
        })
    }

    // GETs Current User Order. Modify endpoint once defined
    let asyncCallCurrentUserOrder = async () => {
        // Check headers
        let res = await fetch('http://localhost:8080/orders/' + props.username + '/current', {
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then((data) => data.json())
        .then((data) => setCurrentOrder(data))
        .catch((error) => {
            alert("There was an error loading order")
            console.log(error)
        })
    }
    

    useEffect(() => {

        if (props.isAdmin) {
            asyncCallAllOrders()
        } else {
            asyncCallUserOrders()
            asyncCallCurrentUserOrder()
        }
    
    },[])
        

    return (
        <>
            <h1>Orders</h1>
            {/* Inner Nav Component */}
            {
                (!props.isAdmin && currentOrder !== null) && (
                    <div className='currentOrder'>
                        <Order {...currentOrder}/>
                    </div>
                )
            }
            {
                orderList.map((order: IOrder) => {
                    return (
                        <Order {...order}/>
                    )
            }
            )}
        </>
    )

}

export default OrderPage