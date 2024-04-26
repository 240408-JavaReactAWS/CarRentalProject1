import React from 'react';
import VehicleForm from './VehicleForm';
import VehicleList from './VehicleList';

const VehicleDashboard = () => {
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