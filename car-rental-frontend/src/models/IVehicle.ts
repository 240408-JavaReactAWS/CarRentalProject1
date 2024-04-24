import { ILocation } from "./ILocation";

export interface IVehicle {
    id: number,
    color: string,
    make: string,
    model: string,
    year: string,
    location: ILocation,
    isAvailable: boolean
}