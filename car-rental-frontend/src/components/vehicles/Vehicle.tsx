import React, {useState, useEffect} from 'react';
import VehicleInfo from './VehicleInfo';
import Button from '../Button';
import { IVehicle } from '../../models/IVehicle';
import { Source, IButtonProps } from '../../models/IButtonProps';
import axios from 'axios';

function Vehicle(props: IVehicle) {

    const [vehicle, setVehicle] = useState<any>(props);

    let availability = vehicle.available ? 'available' : 'unavailable';

    let transferVehicle = (newLocationId: number) => {
        let asyncCall = async () => {
            try {
                let res = await axios.patch('http://localhost:8080/vehicles/' + vehicle.vehicleId, {
                    locationId: newLocationId
                })
                if (res.status != 200) {
                    throw new Error("Error: " + res.status)
                }
                setVehicle(res.data)
            } catch (error: any) {
                console.log("Error: " + error)
            }
        }

        asyncCall()
    }

    return (
        <>
            <div>
                <div>
                    <VehicleInfo {...vehicle}/>
                </div>
                <div>
                    <div className={availability}></div>
                    <Button source = {Source.Vehicle}
                            sourceId= {vehicle.vehicleId}
                            shouldDisplay= {vehicle.available}
                            methods= {{transferVehicle: transferVehicle}}
                    />
                </div>
            </div>
        </>
    )

}

export default Vehicle;