interface InnerNavProps {
    asyncCallAllOrders: () => void,
    asyncCallPendingOrders: () => void,
    asyncCallCompletedOrders: () => void
}

function OrderNav(props: InnerNavProps) {

    return (
        <div>
            <button onClick={props.asyncCallAllOrders}>All</button>
            <button onClick={props.asyncCallPendingOrders}>Pending</button>
            <button onClick={props.asyncCallCompletedOrders}>Completed</button>
        </div>
    )

}

export default OrderNav