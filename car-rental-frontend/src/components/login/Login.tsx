import React, { SyntheticEvent, useState, useEffect, useContext } from 'react';
import './Login.css';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import { UserContext } from '../../App';

function Login() {

    const {isLoggedIn, setIsLoggedIn, isAdmin, setIsAdmin} = useContext(UserContext)

    const location = useLocation();
    const navigate = useNavigate();
    
    const [storedUsername, setStoredUsername] = useState<string>('')
    const [storedPassword, setStoredPassword] = useState<string>('')

    let changeUsername = (e: SyntheticEvent) => {
        setStoredUsername((e.target as HTMLInputElement).value)
    }

    let changePassword = (e: SyntheticEvent) => {
        setStoredPassword((e.target as HTMLInputElement).value)
    }

    useEffect(() => { 
        let logout = async () => {
            try {
                let res = await axios.get('http://localhost:8080/users/logout', {
                    withCredentials: true
                })
                if (res.status === 200) {
                    console.log("Logout Successful")
                    setIsLoggedIn(false)
                }
            } catch (error) {
                alert("There was an error logging out")
                console.log(error)
            }
        }

        if (isLoggedIn) {
            logout()
        }
    },[])

    let login = async () => {
        if (storedUsername != "" && storedPassword != "") {
            // let res = await fetch('http://localhost:8080/users/login', {
            //     method: 'POST',
            //     headers: {
            //         'Content-Type': 'application/json'
            //     },
            //     body: JSON.stringify({
            //         username: storedUsername,
            //         password: storedPassword
            //     }),
            //     credentials: 'include'
            // })
            // .then((data) => data.json())
            // .then((data) => {
            //     localStorage.setItem('user', JSON.stringify(data))
            // })
            // .catch((error) => {
            //     alert("There was an error logging in")
            //     console.log(error)
            // })

            try {
                let res = await axios.post('http://localhost:8080/users/login', {
                    username: storedUsername,
                    password: storedPassword
                }, {
                    withCredentials: true
                });
                if (res.status === 200) {
                    console.log("Login Successful")
                    setIsLoggedIn(true)
                    navigate('/')
                }
            } catch (error) {
                alert("There was an error logging in")
                console.log(error)
            }

            setStoredUsername('')
            setStoredPassword('')

        }

        //console.log(localStorage.getItem('user'))
    }

    let createAccount = async (isAdmin: boolean) => {
        if (storedUsername != "" && storedPassword != "") {
            // let res = fetch('http://localhost:8080/users/register', {
            //     method: 'POST',
            //     headers: {
            //         'Content-Type': 'application/json'
            //     },
            //     body: JSON.stringify({
            //         username: storedUsername,
            //         password: storedPassword,
            //         isAdmin: isAdmin
            //     }),
            //     credentials: 'include'
            // })
            // .then((data) => data.json())
            // .then((data) => {
            //     localStorage.setItem('userId', JSON.stringify(data.userId))
            //     localStorage.setItem('admin', JSON.stringify(data.isAdmin))
            // })
            // .catch((error) => {
            //     alert("There was an error creating an account")
            //     console.log(error)
            // })

            try {
                let res = await axios.post('http://localhost:8080/users/register', {
                    username: storedUsername,
                    password: storedPassword,
                    isAdmin: isAdmin
                }, {
                    withCredentials: true
                });
                if (res.status === 200) {
                    console.log("Account Created Successfully")
                    setIsLoggedIn(true)
                    navigate('/')
                }
            } catch (error) {
                alert("There was an error creating account")
                console.log(error)
            }

            setStoredUsername('')
            setStoredPassword('')
        }

        //console.log(localStorage.getItem('user'))
    }


    return (
        <>
            <h1 className='contentHeading'>Login</h1>
            <div className='contentBody'>
            
                <input type="text" value={storedUsername} placeholder="Username" onChange={changeUsername} />
                <input type="password" value={storedPassword} placeholder="Password" onChange={changePassword} />
                
                <div className='buttonSet'>
                    <button className="login" onClick={login}>Login</button>
                    <button className="login" onClick={() => createAccount(false)}>Create Account</button><br />
                </div>
                <button onClick={() => createAccount(true)}>Create Admin Account</button>
            </div>
        </>
    )

}

export default Login;