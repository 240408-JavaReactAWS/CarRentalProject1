import './OrderNav.css'

interface InnerNavProps {
    asyncCallAllOrders: () => void,
    asyncCallPendingOrders: () => void,
    asyncCallCompletedOrders: () => void,
    //filterOrders: (e: React.MouseEvent<HTMLButtonElement>) => void
}

function OrderNav(props: InnerNavProps) {

    return (
        <div className='orderNav'>
            <button value={"all"} onClick={props.asyncCallAllOrders}>All</button>
            <button value={"pending"} onClick={props.asyncCallPendingOrders}>Pending</button>
            <button value={"completed"} onClick={props.asyncCallCompletedOrders}>Completed</button>
        </div>
    )

}

export default OrderNav