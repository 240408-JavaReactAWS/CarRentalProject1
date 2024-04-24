import { IVehicle } from "./IVehicle";

export interface ILocation {
    locationId: number,
    streetAddress: string,
    city: string,
    state: string,
    postalCode: string,
    vehicleStock: IVehicle[]
}