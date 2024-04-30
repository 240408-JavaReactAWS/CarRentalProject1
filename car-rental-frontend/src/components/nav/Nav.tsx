import React, {useState, useEffect, useContext} from 'react'
import{ Link, useNavigate } from 'react-router-dom'
import './Nav.css'
import { commonFunctions } from '../../common-functions'
import { UserContext } from '../../App'

function Nav() {

    const {isLoggedIn, setIsLoggedIn, isAdmin, setIsAdmin} = useContext(UserContext)

    const navigate = useNavigate()

    useEffect(() => {
        let asyncCall = async () => {
            let validateSession = await commonFunctions.validateSession();
            if (validateSession) {
                setIsLoggedIn(true);
                let isAdmin = await commonFunctions.isAdmin();
                if (isAdmin) {
                    setIsAdmin(true);
                } else {
                    setIsAdmin(false);
                }
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
                {isAdmin ?
                <Link to="/admin/vehicles">Admin::Vehicles</Link> : null}
            </li>
            <li>
                {isAdmin ?
                <Link to="/admin/locations">Admin::Locations</Link> : null}
            </li>
            <li>    
                {!isLoggedIn ? 
                <Link to="/login" >Login</Link> 
                : <Link to="/login" >Logout</Link>}
            </li>
        </ul>
    </nav>
  )
}

export default Nav
