export enum Source {
    Vehicle,
    Order
}

export interface IButtonProps {
    source: Source,
    sourceId: number,
    shouldDisplay: boolean    
}