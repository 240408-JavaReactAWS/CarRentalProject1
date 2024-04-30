export enum Source {
    Vehicle,
    Order
}

export interface IButtonProps {
    source: Source,
    sourceId: number,
    shouldDisplay: boolean,
    methods : {
        approveReject?: (approval: boolean) => void,
        cancelOrder?: ()  => void,
        pickUpOrder?: () => void,
        returnOrder?: () => void,
        transferVehicle?: (newLocationId: number) => void,
        // editVehicle?: () => void
        // orderVehicle?: () => void
    }
}