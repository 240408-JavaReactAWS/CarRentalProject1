import { IVehicle } from "./IVehicle";
import { IUser } from "./IUser";

export interface IOrder {
    orderId: number,
    dateAndTime: string,
    vehicle: IVehicle,
    user: IUser,
    isApproved: boolean,
    isCompleted: boolean
}