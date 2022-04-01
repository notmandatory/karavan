import React from 'react'
import './App.css'
import axios from 'axios'

import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login                            from "./Pages/Login";
import RouteUrls from "./Routes/route-urls";
import Catalog from "./Pages/Catalog";
import API1 from "./Pages/API1";
import API2 from "./Pages/API2";
import API3 from "./Pages/API3";
import API4 from "./Pages/API4";


function App() {
    return (
        <div className="App">
            <BrowserRouter>
                <Routes>
                    <Route path={RouteUrls.LOGIN} element={<Login/>} exact/>
                </Routes>
                <Routes>
                    <Route path={RouteUrls.Catalog} element={<Catalog/>} exact/>
                </Routes>
                <Routes>
                    <Route path={RouteUrls.API1} element={<API1/>} exact/>
                </Routes>
                <Routes>
                    <Route path={RouteUrls.API2} element={<API2/>} exact/>
                </Routes>
                <Routes>
                    <Route path={RouteUrls.API3} element={<API3/>} exact/>
                </Routes>
                <Routes>
                    <Route path={RouteUrls.API4} element={<API4/>} exact/>
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;

// class App extends Component {
//     constructor() {
//         super()
//         this.handleClickOpen = this.handleClickOpen.bind(this)
//     }
//
//     render () {//show buttons to click on
//         return (
//             <div className='button__container'>
//                 <ol>
//                     <li><button className='button' onClick={this.handleClickOpen}>Open Wallet</button></li>
//                     <li><button className='button' onClick={this.handleClickClose}>Close Wallet</button></li>
//                 </ol>
//
//             </div>
//         )
//     }
//     handleClickOpen () { //open wallet request
//         const data = {
//             "network": "testnet",
//             "descriptor": "wpkh([1f44db3b/84'/1'/0'/0]tpubDEtS2joSaGheeVGuopWunPzqi7D3BJ9kooggvasZWUzSVziMNKkrdfS7VnLDe6M4Cg6bw3j5oxRB5U7GMJGcFnDia6yUaFAdwWqyJQjn4Qp/0/*)"
//         }
//         axios.put('http://localhost:8080/wallet', data, {withCredentials: true})
//             .then(response => console.log(response)) //print wallet request
//     }
//     handleClickClose () {
//         axios.delete('http://localhost:8080/wallet', {withCredentials: true}).then(response => console.log(response))
//         //this doesn't work, cookies not stored on system
//     }
// }
// export default App