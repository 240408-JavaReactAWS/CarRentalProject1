import React, { SyntheticEvent, useState } from 'react';
import './Login.css';
import axios from 'axios';

function Login() {

    let storedUsername = ''
    let storedPassword = ''

    let changeUsername = (e: SyntheticEvent) => {
        storedUsername = (e.target as HTMLInputElement).value
    }

    let changePassword = (e: SyntheticEvent) => {
        storedPassword = (e.target as HTMLInputElement).value
    }

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
                }
            } catch (error) {
                alert("There was an error logging in")
                console.log(error)
            }

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
                let res = await axios.post('http://localhost:8080/users/login', {
                    username: storedUsername,
                    password: storedPassword,
                    isAdmin: isAdmin
                }, {
                    withCredentials: true
                });
                if (res.status === 200) {
                    console.log("Login Successful")
                }
            } catch (error) {
                alert("There was an error creating account")
                console.log(error)
            }
        }

        //console.log(localStorage.getItem('user'))
    }


    return (
        <>
            <h1 className='contentHeading'>Login</h1>
            <div className='contentBody'>
                <input type="text" placeholder="Username" onChange={changeUsername} />
                <input type="password" placeholder="Password" onChange={changePassword} />
                <div className='buttonSet'>
                    <button onClick={login}>Login</button>
                    <button onClick={() => createAccount(false)}>Create Account</button><br />
                </div>
                <button onClick={() => createAccount(true)}>Create Admin Account</button>
            </div>
        </>
    )

}

export default Login;