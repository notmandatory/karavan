import React, { Component, useState, useEffect } from "react";
import styled, { css } from "styled-components";
import RefreshButton from "../components/RefreshButton";
import MaterialIconTextbox from "../components/MaterialIconTextbox";
import AmountInput from "../components/AmountInput";
import MaterialIconTextbox1 from "../components/MaterialIconTextbox1";
import CupertinoButtonInfo1 from "../components/CupertinoButtonInfo1";
import EntypoIcon from "react-native-vector-icons/dist/Entypo";
import MaterialIconTextbox2 from "../components/MaterialIconTextbox2";
import FeatherIcon from "react-native-vector-icons/dist/Feather";
import { AdvancedRealTimeChart } from "react-ts-tradingview-widgets";
import { withCookies, Cookies } from "react-cookie";
import {decode as base64_decode, encode as base64_encode} from 'base-64';
import { Link, useHistory } from "react-router-dom";
import { Table, Tag, Space } from 'antd';
import axios from "axios";

function Dashboard(props) {

    const history = useHistory();

    const [balance, setBalance] = useState(5315)
    const [data, setData] = useState(null)

    const [myAddress, setMyAddress] = useState("tb1qvqjvm5z0c8z7239q5rpexgc4krjdtz3xjsedetx9zuzya8lg3dxsjqx2ph")

    const [address, setAddress] = useState("tb1ql7w62elx9ucw4pj5lgw4l028hmuw80sndtntxt")
    const [amount, setAmount] = useState(4500)
    const [feeRate, setFeeRate] = useState(1)

    const [refresh, setRefresh] = useState(false)

    const [psbt, setPSBT] = useState("Your PSBT willl appear hear after you create a transaction")

    const [psbtForTransaction, setPSBTForTransaction] = useState(null)

    const columns = [
        {
            title: 'Taxid',
            dataIndex: 'txid',
            key: 'txid',
        },
        {
            title: 'Fee',
            dataIndex: 'fee',
            key: 'fee',
        },
        {
            title: 'Received',
            dataIndex: 'received',
            key: 'received',
        },
        {
            title: 'Sent',
            dataIndex: 'sent',
            key: 'sent',
        },
    ];

    function getAddress() {
        console.log("getAddress")
        axios.get('http://localhost:8080/wallet/address/new', {withCredentials: true}).then(response => setMyAddress(response.data))
    }

    function getTransactions() {
        console.log("getTransactions")
        axios.get('http://localhost:8080/wallet/transactions', {withCredentials: true}).then( (response) => {

            let transactions = response.data.map((line,key)=>{
                console.log(line)
                return {
                    key: key,
                    txid: line.details.txid,
                    fee: line.details.fees,
                    received: line.details.received,
                    sent: line.details.sent,
                }
            })

            setData(transactions)

        })
    }

    useEffect(() => {
        setTimeout(() => {
            getBalance()
            getAddress()
            getTransactions()
        }, 3000);
    });

    function handleAddressChange(e) {
        console.log("addressChange")
        console.log(e.target.value)
        //setAddress(e.target.value)
    }

    function handleAmountChange(e) {
        console.log("amountChange")
        console.log(e.target.value)
        //setAmount(e.target.value)
    }

    function handleFeeRateChange(e) {
        console.log("feeRateChange")
        console.log(e.target.value)
        //setFeeRate(e.target.value)
    }

    function handlePSBT(e) {
        console.log("psbtChange")
        console.log(e.target.value)
        setPSBTForTransaction(e.target.value)
    }


    // getBalance function
    function getBalance () {

        console.log("getBalance")

        axios.get('http://localhost:8080/wallet/balance',{withCredentials: true}).then(response => setBalance(response.data.balance))

    }

    // getBalance function
    function createTransaction () {

        console.log("createTransaction")
        //`http://localhost:8080/wallet/transaction/?recipient=tb1ql7w62elx9ucw4pj5lgw4l028hmuw80sndtntxt&amount=4500&fee_rate=1`
        axios.get('http://localhost:8080/wallet/transaction/?recipient=tb1ql7w62elx9ucw4pj5lgw4l028hmuw80sndtntxt&amount=4500&fee_rate=1', {withCredentials: true}).then(response => setPSBT(response.data))
        //axios.get(`http://localhost:8080/wallet/transaction/?recipient=${address}&amount=${amount}&fee_rate=${feeRate}`, {withCredentials: true}).then(response => setPSBT(response.data))

    }

    function broadcast () {

        console.log("broadcast")
        const data = {
            "payload": psbtForTransaction,
            // "payload": "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",
        }
        axios.put('http://localhost:8080/wallet/broadcast', data, {withCredentials: true}).then(response => console.log(response))

    }

    function closeWallet () {

        console.log("closeWallet")
        axios.delete('http://localhost:8080/wallet', {withCredentials: true}).then(response => console.log(response))

        history.push("/");

    }

    return (
        <Background>
            <HeaderGroup>
                <Karavan1>karavan</Karavan1>
                <Karavan1Filler></Karavan1Filler>
                <LogOut2 onClick={closeWallet}>Log Out</LogOut2>
            </HeaderGroup>
            <BalanceModuleRow>
                <BalanceModule>
                    <BalanceLabel>Balance</BalanceLabel>
                    <Balance>{`${balance} sat`}</Balance>
                    <RefreshButton
                        style={{
                            height: 56,
                            width: 56,
                            marginTop: 9,
                            alignSelf: "center"
                        }}
                        onClick={getBalance}
                    ></RefreshButton>
                    <YourAddressOutput>
                        {`Your Address: ${myAddress}`}
                    </YourAddressOutput>
                </BalanceModule>
                <BtcModule>
                    <AdvancedRealTimeChart theme="light" autosize symbol="BTCUSD"></AdvancedRealTimeChart>
                </BtcModule>
            </BalanceModuleRow>
            <TransactionGroup>
                <CreateTxModule>
                    <CreateATransaction>Create a Transaction</CreateATransaction>
                    <CreateTxInputGroup>
                        <MaterialIconTextbox
                            onChange={handleAddressChange}
                            style={{
                                height: 43,
                                width: 375,
                                borderWidth: 1,
                                borderColor: "#000000",
                                borderBottomWidth: 3,
                                borderStyle: "solid",
                                borderRightWidth: 3
                            }}
                        ></MaterialIconTextbox>
                        <AmountInput
                            onChange={handleAmountChange}
                            style={{
                                height: 43,
                                width: 375,
                                borderWidth: 1,
                                borderColor: "#000000",
                                borderRightWidth: 3,
                                borderBottomWidth: 3,
                                borderStyle: "solid"
                            }}
                        ></AmountInput>
                        <MaterialIconTextbox1
                            onChange={handleFeeRateChange}
                            style={{
                                height: 43,
                                width: 375,
                                borderWidth: 1,
                                borderColor: "#000000",
                                borderRightWidth: 3,
                                borderBottomWidth: 3,
                                borderStyle: "solid"
                            }}
                        ></MaterialIconTextbox1>
                        <CupertinoButtonInfo1
                            style={{
                                height: 44,
                                alignSelf: "stretch"
                            }}
                            onClick={createTransaction}
                        ></CupertinoButtonInfo1>
                    </CreateTxInputGroup>
                </CreateTxModule>
                <SignTxModule>
                    <SignYourTx>Sign Your Transaction</SignYourTx>
                    <SignInstructions>
                        Here is your PSBT, please have all parties sign it. When you are
                        done, come back here to broadcast it.
                    </SignInstructions>
                    <PsbtGroup>
                        <PSbtOutput
                            style={{
                                height: 40,
                                width: 400,
                                // inlineSize: "150px",
                                // overflowWrap: "break-word",
                                overflowY: "hidden"
                            }}
                        >
                            {psbt}
                        </PSbtOutput>
                        <EntypoIcon
                            name="copy"
                            style={{
                                color: "rgba(0,122,255,1)",
                                fontSize: 40,
                                alignSelf: "center"
                            }}
                        ></EntypoIcon>
                    </PsbtGroup>
                </SignTxModule>
                <BroadcastTxModule>
                    <BroadcastYourTx>Broadcast Your Transaction</BroadcastYourTx>
                    <BroadcastInstructions>
                        Paste your finalized PSBT here and we&#39;ll broadcast it to the
                        blockchain.
                    </BroadcastInstructions>
                    <FinalizedPsbtGroup>
                        <MaterialIconTextbox2
                            onChange={handlePSBT}
                            style={{
                                height: 43,
                                width: 375
                            }}
                        ></MaterialIconTextbox2>
                        <FeatherIcon
                            name="radio"
                            style={{
                                color: "rgba(0,122,255,1)",
                                fontSize: 40,
                                marginRight: 8,
                                marginLeft: 8
                            }}
                            onClick={broadcast}
                        ></FeatherIcon>
                    </FinalizedPsbtGroup>
                </BroadcastTxModule>
            </TransactionGroup>

            <TransactionGroup>
                <Table columns={columns} dataSource={data}
                       style={{
                           height: 900,
                           width: 1800
                }}/>
            </TransactionGroup>

        </Background>
    );
}

const Background = styled.div`
  display: flex;
  background-color: rgba(235,232,232,1);
  flex-direction: column;
  height: 150vh;
  width: 100vw;
`;

const HeaderGroup = styled.div`
  height: 52px;
  background-color: rgba(0,122,255,1);
  flex-direction: row;
  display: flex;
`;

const Karavan1 = styled.span`
  font-family: Helvetica;
  font-style: normal;
  font-weight: 400;
  color: rgba(255,255,255,1);
  font-size: 28px;
  width: 118px;
  height: 39px;
  margin-left: 130px;
  margin-top: 5px;
`;

const Karavan1Filler = styled.div`
  flex: 1 1 0%;
  flex-direction: row;
  display: flex;
`;

const LogOut2 = styled.span`
  font-family: Helvetica;
  font-style: normal;
  font-weight: 400;
  color: rgba(255,255,255,1);
  font-size: 18px;
  width: 72px;
  height: 23px;
  margin-right: 103px;
  margin-top: 14px;
`;

const BalanceModule = styled.div`
  width: 786px;
  height: 320px;
  background-color: rgba(255,255,255,1);
  flex-direction: column;
  display: flex;
  margin-top: -1px;
  box-shadow: 3px 3px 10px  0.01px rgba(0,0,0,1) ;
`;

const BalanceLabel = styled.span`
  font-family: Helvetica;
  font-style: normal;
  font-weight: 400;
  color: #121212;
  font-size: 24px;
  margin-top: 34px;
  align-self: center;
`;

const Balance = styled.span`
  font-family: Helvetica;
  font-style: normal;
  font-weight: 400;
  color: #121212;
  font-size: 36px;
  
  height: 66px;
  margin-top: 26px;
  align-self: center;
`;

const YourAddressOutput = styled.span`
  font-family: Helvetica;
  font-style: normal;
  font-weight: 400;
  color: #121212;
  margin-top: 40px;
  align-self: center;
  font-size: 15px;
`;

const BtcModule = styled.div`
  width: 794px;
  height: 320px;
  background-color: #fff;
  margin-left: 105px;
  margin-top: -1px;
  box-shadow: 3px 3px 10px  0.01px rgba(0,0,0,1) ;
`;

const BalanceModuleRow = styled.div`
  height: 320px;
  flex-direction: row;
  display: flex;
  margin-top: 35px;
  margin-left: 124px;
  margin-right: 111px;
`;

const TransactionGroup = styled.div`
  height: 413px;
  flex-direction: row;
  justify-content: space-between;
  background-color: rgba(255,255,255,1);
  overflow: visible;
  width: 1685px;
  margin-top: 52px;
  margin-left: 124px;
  display: flex;
  box-shadow: 3px 3px 10px  0.01px rgba(0,0,0,1) ;
`;

const CreateTxModule = styled.div`
  width: 562px;
  height: 413px;
  flex-direction: column;
  display: flex;
`;

const CreateATransaction = styled.span`
  font-family: Helvetica;
  font-style: normal;
  font-weight: 400;
  color: #121212;
  font-size: 30px;
  margin-top: 40px;
  margin-left: 139px;
`;

const CreateTxInputGroup = styled.div`
  width: 377px;
  height: 274px;
  flex-direction: column;
  justify-content: space-between;
  margin-top: 19px;
  margin-left: 130px;
  display: flex;
`;

const SignTxModule = styled.div`
  width: 562px;
  flex-direction: column;
  display: flex;
`;

const SignYourTx = styled.span`
  font-family: Helvetica;
  font-style: normal;
  font-weight: 400;
  color: #121212;
  font-size: 30px;
  margin-top: 40px;
  align-self: center;
`;

const SignInstructions = styled.span`
  font-family: Helvetica;
  font-style: normal;
  font-weight: 400;
  color: #121212;
  width: 224px;
  height: 77px;
  text-align: center;
  margin-top: 42px;
  margin-left: 169px;
`;

const PsbtGroup = styled.div`
  width: 427px;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 72px;
  margin-left: 101px;
  display: flex;
`;

const PSbtOutput = styled.span`
  font-family: Helvetica;
  font-style: normal;
  font-weight: 400;
  color: rgba(0,0,0,1);
  align-self: center;
`;

const BroadcastTxModule = styled.div`
  width: 562px;
  height: 413px;
  flex-direction: column;
  display: flex;
`;

const BroadcastYourTx = styled.span`
  font-family: Helvetica;
  font-style: normal;
  font-weight: 400;
  color: #121212;
  font-size: 30px;
  margin-top: 40px;
  margin-left: 94px;
`;

const BroadcastInstructions = styled.span`
  font-family: Helvetica;
  font-style: normal;
  font-weight: 400;
  color: #121212;
  width: 224px;
  height: 77px;
  text-align: center;
  margin-top: 42px;
  margin-left: 166px;
`;

const FinalizedPsbtGroup = styled.div`
  width: 454px;
  height: 45px;
  flex-direction: row;
  justify-content: space-between;
  border-width: 1px;
  border-color: #000000;
  border-right-width: 3px;
  border-bottom-width: 3px;
  margin-top: 61px;
  align-self: center;
  border-style: solid;
  display: flex;
`;

export default Dashboard;
