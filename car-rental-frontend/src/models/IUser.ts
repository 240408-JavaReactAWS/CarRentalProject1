import {IVehicle} from "./IVehicle";
import {IOrder} from "./IOrder";

export interface IUser {
    userId: number,
    username: string,
    password: string,
    currentCar: IVehicle,
    allOrders: IOrder[],
    isAdmin: boolean
}