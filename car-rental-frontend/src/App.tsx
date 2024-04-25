import React from 'react';
import logo from './logo.svg';
import './App.css';
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import OrderPage from './components/orders/OrderPage';
import Nav from './components/nav/Nav';
import Login from './components/login/Login';


function App() {

  //console.log(localStorage.user)

  return (
    <div className="content">
      <header className="header">
        <h1>Rent-All's Rentals</h1>
      </header>
      <BrowserRouter>
        {/* Replace with Nav Component */}
        <Nav />
        <Routes>
          <Route path="/" element={<Login />} />
          {/* <Route path="/vehicles" element={<VehiclePage />} /> */}
          <Route path="/orders" element={<OrderPage {...localStorage.user} />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
