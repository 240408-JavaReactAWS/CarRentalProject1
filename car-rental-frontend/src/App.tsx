import React from 'react';
import logo from './logo.svg';
import './App.css';
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import OrderPage from './components/orders/OrderPage';
import Nav from './components/nav/Nav';


function App() {
  return (
    <div className="content">
      <header className="header">
        <h1>Rent-All's Rentals</h1>
      </header>
      <BrowserRouter>
        {/* Replace with Nav Component */}
        <Nav />
        <Routes>
          {/* <Route path="/" element={<Home />} /> */}
          {/* <Route path="/vehicles" element={<VehiclePage />} /> */}
          <Route path="/orders" element={<OrderPage {...localStorage.user} />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
