import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from "react-router-dom";

import './css/bootstrap-icons.css';
import './css/bootstrap.min.css';
import './css/tinycms.css';

import Home from "./pages/Home";
import Collection from "./pages/Collection";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />}>
          <Route index element={<Home />} />
        </Route>
        <Route path="/collection/*" element={<Collection />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);
