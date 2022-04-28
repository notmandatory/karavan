import React, { Component } from "react";
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

function Dashboard(props) {
  return (
    <Background>
      <HeaderGroup>
        <Karavan1>karavan</Karavan1>
        <Karavan1Filler></Karavan1Filler>
        <LogOut2>Log Out</LogOut2>
      </HeaderGroup>
      <BalanceModuleRow>
        <BalanceModule>
          <BalanceLabel>Balance</BalanceLabel>
          <Balance>30642 Sat(s)</Balance>
          <RefreshButton
            style={{
              height: 56,
              width: 56,
              marginTop: 9,
              alignSelf: "center"
            }}
          ></RefreshButton>
          <YourAddressOutput>
            Your Address: tb1q0fsteqef9aydnmgtg4sefh9nrskfzcafc5r2nwy9tq43n2g0mp9snhram2
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
            <PSbtOutput>
              Your PSBT willl appear hear after you create a transaction
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
            ></FeatherIcon>
          </FinalizedPsbtGroup>
        </BroadcastTxModule>
      </TransactionGroup>
    </Background>
  );
}

const Background = styled.div`
  display: flex;
  background-color: rgba(235,232,232,1);
  flex-direction: column;
  height: 100vh;
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
  margin-top: 11px;
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
  width: 220px;
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
  height: 413px;
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
  height: 44px;
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
