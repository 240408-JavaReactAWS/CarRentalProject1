import React, {useState, useEffect} from 'react'
import{ Link, useNavigate } from 'react-router-dom'
import './Nav.css'
import { commonFunctions } from '../../common-functions'

function Nav() {

    const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false)
    const navigate = useNavigate()

    useEffect(() => {
        let asyncCall = async () => {
            let validateSession = await commonFunctions.validateSession();
            if (validateSession) {
                setIsLoggedIn(true);
            } else{
                setIsLoggedIn(false);
            }
        }
        asyncCall();
    }, [window.location.pathname]);

  return (
    <nav className='mainNav'>
        <ul>
            <li>
                <Link to="/">Home</Link>
            </li>    
            <li>
                <Link to="/locations">Locations</Link>
            </li>
            <li>
                <Link to="/orders">Orders</Link>
            </li> 
            <li>    
                {!isLoggedIn ? 
                <Link to="/login" state={{isLoggedIn: isLoggedIn}}>Login</Link> 
                : <Link to="/login" state={{isLoggedIn: isLoggedIn}}>Logout</Link>}
            </li>
        </ul>
    </nav>
  )
}

export default Nav
