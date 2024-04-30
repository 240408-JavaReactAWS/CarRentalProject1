import React, {createContext, useState} from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import Home from './components/home/Home';
import OrderPage from './components/orders/OrderPage';
import Nav from './components/nav/Nav';
import Login from './components/login/Login';
import VehicleDashboard from './components/vehicles/VehicleDashboard';
import LocationPage from './components/locations/LocationPage';

interface userContextInterface {
  isLoggedIn: boolean,
  setIsLoggedIn: Function,
  isAdmin: boolean,
  setIsAdmin: Function

}

export const UserContext = createContext<userContextInterface>({isLoggedIn: false, setIsLoggedIn: () => {}, isAdmin: false, setIsAdmin: () => {}})

function App() {

  //console.log(localStorage.user)

  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false)
  const [isAdmin, setIsAdmin] = useState<boolean>(false)

  return (
    <div className="content">
      <header className="header">
        <h1>Rent-All's Rentals</h1>
      </header>
      <UserContext.Provider value={{isLoggedIn, setIsLoggedIn, isAdmin, setIsAdmin}}>
        <BrowserRouter>
          {/* Replace with Nav Component */}
          <Nav />
          <Routes>
            <Route path="/" element={<Home />} />
            {/* <Route path="/vehicles" element={<VehiclePage />} /> */}
            <Route path="/locations" element={<LocationPage />} />
            <Route path="/orders" element={<OrderPage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/admin/vehicles" element={<VehicleDashboard />} />
            {/* <Route path="/admin/locations" element={<LocationDashboard />} /> */}
          </Routes>
        </BrowserRouter>
      </UserContext.Provider>
    </div>
  );
}

export default App;
