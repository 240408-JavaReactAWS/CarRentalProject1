import React, {SyntheticEvent, useEffect, useState} from 'react';

import { IButtonProps, Source } from '../models/IButtonProps';
import { IUser } from '../models/IUser';
import axios from 'axios';
import { commonFunctions } from '../common-functions';
import './Button.css';

function Button(props: IButtonProps) {


    /*
    let user : IUser;

    if (!localStorage.getItem('user')) {
        return null;
    } else {
        user = JSON.parse(localStorage.getItem('user') || '{}');
    }
    */

    let [isAdmin, setIsAdmin] = useState<boolean>(false)
    let [isCompleted, setIsCompleted] = useState<boolean | undefined>(props.isCompleted)
    let [hasCar, setHasCar] = useState<boolean>(false)
    

    useEffect(() => {

        let asyncCall = async () => {
            let isAdmin = await commonFunctions.isAdmin();
            let hasCar = await commonFunctions.hasCar();
            setIsAdmin(isAdmin);
            setHasCar(hasCar);

        }

        asyncCall();

     })

    // If Button is A Part of the Vehicle Component
    if (props.source == Source.Vehicle) {
        // If User is Admin
        if (isAdmin) {
            // Display Edit/Transfer Buttons

            // Edit Button - Should open a modal with input fields for the user to edit the vehicle information. This should also include a submit button to save the changes.
            // Transfer Button - Should open a modal with a dropdown of all locations. The user should be able to select a location and click submit to transfer the vehicle to that location.
            return (
                <>
                    <div className='ButtonDiv'>
                        <button>Edit</button>
                        <button>Transfer</button>
                    </div>
                </>
            )
        }
        // If User is Not Admin && If Vehicle is Available
        else if (props.shouldDisplay) {
            // Display Order Button

            // Order Button - Should submit a post request to the server to create a new order.
            return (
                <>
                    <div className='ButtonDiv'>
                        <button>Order</button>
                    </div>
                </>
            )
        }

    } 
    // If Button is A Part of the Order Component
    else if (props.source == Source.Order) {
        // If User is Admin
        if (isAdmin && !props.isApproved && !isCompleted) {
            // If Order isCompleted is False
            // Display Approve/Reject Buttons

            // Both Buttons - Call a function that will send a patch request to the server, updating either the order status to approved or the order completion status to true.
            return (
                <>
                    <div className='ButtonDiv'>
                        <button onClick={() => props.methods?.approveReject?.(true)}>Approve</button>
                        <button className='delete' onClick={() => props.methods?.approveReject?.(false)}>Reject</button>
                    </div>
                </>
            )
        }
        // If User is Not Admin
        else if (!isAdmin && !isCompleted) {
            // If Order isCompleted is False
                // If User Current Car is Null
                if (!hasCar) {
                    // Display Cancel Button and Pick Up Button

                    // Cancel Button - Does this delete the order or simply mark complete?
                    // Pick Up Button - Should submit a patch request to the server to update the user's current car to the vehicle in the order.
                    return (
                        <>
                            <div className='ButtonDiv'>
                                {props.isApproved && !isCompleted ?
                                <button onClick={() => {props.methods?.pickUpOrder?.()
                                    setHasCar(true)
                                }}>Pick Up</button> :
                                <button className='delete' onClick={() => {props.methods?.cancelOrder?.()
                                    setHasCar(false)
                                    window.location.reload()
                                }}>Cancel</button> 
                                }
                            </div>
                        </>
                    )
                } else {
                    // Display Return Button

                    // Return Button - Should submit a patch request to the server to update the user's current car to null and mark the order as complete.
                    return (
                        <>
                            <div className='ButtonDiv'>
                                <button onClick= {() => {props.methods?.returnOrder?.()
                                    setHasCar(false)
                                    setIsCompleted(true)
                                }}>Return</button>
                            </div>
                        </>
                    )
                }
        }
    }

    return null;
}

export default Button