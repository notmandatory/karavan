import React from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import "./icons.js";
import Dashboard from "./screens/Dashboard";
import LogIn from "./screens/LogIn";
import "./style.css";

function App() {
  return (
    <Router>
      <Route path="/" exact component={LogIn} />
      <Route path="/Dashboard/" exact component={Dashboard} />
      <Route path="/LogIn/" exact component={LogIn} />
    </Router>
  );
}

export default App;
