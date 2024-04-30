import React, { useEffect } from 'react';
import VehicleForm from './VehicleForm';
import VehicleList from './VehicleList';
import { commonFunctions } from '../../common-functions';
import { useNavigate } from 'react-router-dom';

const VehicleDashboard = () => {

    let navigate = useNavigate();

    useEffect(() => {
        const checkAdmin = async () => {
            let admin = await commonFunctions.isAdmin();
            if (!admin) {
                navigate("/");
            }
        }
        checkAdmin();
    },[]);

    return (
        <div>
            <h1>Vehicle Dashboard</h1>
                <h2>Add Vehicle Form</h2>
                    <VehicleForm />
                <h2>Vehicle List</h2>
                    <VehicleList />
        </div>
    )
}

export default VehicleDashboard;