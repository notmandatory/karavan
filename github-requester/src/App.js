import React, { Component } from 'react'
import './App.css'
import axios from 'axios'
class App extends Component {
    constructor() {
        super()
        this.handleClickOpen = this.handleClickOpen.bind(this)
    }
    render () {//show buttons to click on
        return (
            <div className='button__container'>
                <ol>
                    <li><button className='button' onClick={this.handleClickOpen}>Open Wallet</button></li>
                    <li><button className='button' onClick={this.handleClickClose}>Close Wallet</button></li>
                </ol>

            </div>
        )
    }
    handleClickOpen () { //open wallet request
        const data = {
            "network": "testnet",
            "descriptor": "wpkh([1f44db3b/84'/1'/0'/0]tpubDEtS2joSaGheeVGuopWunPzqi7D3BJ9kooggvasZWUzSVziMNKkrdfS7VnLDe6M4Cg6bw3j5oxRB5U7GMJGcFnDia6yUaFAdwWqyJQjn4Qp/0/*)"
        }
        axios.put('http://localhost:8080/wallet', data)
            .then(response => console.log(response)) //print wallet request
    }
    handleClickClose () {
        axios.delete('http://localhost:8080/wallet').then(response => console.log(response))
        //this doesn't work, cookies not stored on system
    }
}
export default App