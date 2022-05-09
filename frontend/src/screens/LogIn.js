import React, { Component, useState } from "react";
import styled, { css } from "styled-components";
import DescriptorInputBox from "../components/DescriptorInputBox";
import NetworkTextBox from "../components/NetworkTextBox";
import { Link, useHistory } from "react-router-dom";
import LogInButton from "../components/LogInButton";
import Select from 'react-select'
import axios from "axios";
import {decode as base64_decode, encode as base64_encode} from 'base-64';

function LogIn(props) {

    const history = useHistory();

    const [network, setNetwork] = useState(null)
    const [descriptor, setDescriptor] = useState(null)

    // open wallet request function
    function openWallet () {

        let encodedDescriptor = base64_encode(descriptor);

        const data = {
            "network": network,
            "descriptor": "d3NoKG11bHRpKDIsWzI3Mjk1Yjk4Lzg0Jy8xJy8wJy8wXXRwdWJERjZNSGhoaHJXVUx5Y1BGTU05UmkzRHFBYVpVZ3ROMTlOdWY1Y1Z6NVhWVG5xcWlUNTZCV0ZuZlA5ZW01UjUyNmdDSmpRVTY4Nnc3NzhLNVJqaFpmeHRNWmg3N1dETWYxcllVNnpTU3lxMy8qLFs3NzYwNjAzOC84NCcvMScvMCcvMF10cHViREVqVTFLS2JMQzNpTDgyaUg5RnFOZ2o3bmtEVFdOWDk2a3Q2dlBTTVk0a2dkWFVneVR3ZGVhTmZDQlJqQlhCaWtINHRSRW1mNTkxMkFUZ1JuYmZpVUpvcXBTcXFnSnR5NVhUOTlFQmRqNWkvKixbMGQ5MDM3ODIvODQnLzEnLzAnLzBddHB1YkRGMUF4OWMxNXVGNkRMNnJ1RnJ1a0dnTlNvZUtzdFZ1NzhrdFNFQTVDSEtib3FMRmQxRnFaTXBuQjNySGR2NUFUbnhlclN0NlRQTlUxemE1TDVhaDRwVkZGcDdDcGg4c0tIWDZvVlNHMVNrLyopKSN1ZHZ3OGgydg=="
            // "descriptor": encodedDescriptor
        }

        console.log(data)
        axios.put('http://localhost:8080/wallet', data, {withCredentials: true}).then(response => console.log(response))

        history.push("/Dashboard");


    }

    const networkOptions = [
        { value: 'TESTNET', label: 'Testnet' },
        { value: 'BITCOIN', label: 'Bitcoin' },
        { value: 'REGTEST', label: 'Regtest' }
    ]

    function handleInputChange(e) {
        console.log("inputChange")
        console.log(e.target.value)
        setDescriptor(e.target.value)
    }

    function handleSelectChange(e) {
        console.log("selectChange")
        console.log(e.value)
        setNetwork(e.value)
    }

    return (
        <Container>
            <Background>
                <LogInModule>
                    <Rect>
                        <Karavan>karavan</Karavan>
                        <SignIn>Sign In</SignIn>
                        <InputGroup>
                            <DescriptorInputBox
                                onChange={handleInputChange}
                                style={{
                                    height: 61,
                                    width: 388,
                                    borderWidth: 0,
                                    borderColor: "rgba(255,255,255,1)",
                                    borderStyle: "solid",
                                    borderBottomWidth: 1
                                }}
                            ></DescriptorInputBox>
                            <Select options={networkOptions} onChange={handleSelectChange}/>
                            {/*<Link to="/Dashboard">*/}
                            {/*  <Button >*/}
                            {/*    <ButtonOverlay></ButtonOverlay>*/}
                            {/*  </Button>*/}
                            {/*</Link>*/}
                            <LogInButton
                                style={{
                                    height: 53,
                                    width: 350,
                                    alignSelf: "center"
                                }}
                                onClick={openWallet}
                            ></LogInButton>
                        </InputGroup>
                    </Rect>
                </LogInModule>
            </Background>
        </Container>
    );
}

const Container = styled.div`
  display: flex;
  flex-direction: row;
  background-color: rgba(155,155,155,1);
  height: 100vh;
  width: 100vw;
`;

const ButtonOverlay = styled.button`
 display: block;
 background: none;
 height: 100%;
 width: 100%;
 border:none
 `;
const Background = styled.div`
  background-color: rgba(0,0,0,1);
  elevation: 0px;
  shadow-radius: 0px;
  flex-direction: column;
  display: flex;
  justify-content: center;
  flex: 1 1 0%;
  box-shadow: 3px 3px 0px  1px rgba(0,0,0,1) ;
`;

const LogInModule = styled.div`
  width: 450px;
  height: 660px;
  flex-direction: column;
  display: flex;
  justify-content: center;
  align-self: center;
`;

const Rect = styled.div`
  width: 450px;
  height: 660px;
  background-color: rgba(0,0,0,1);
  border-radius: 30px;
  border-width: 5px;
  border-color: rgba(255,255,255,1);
  flex-direction: column;
  display: flex;
  border-style: solid;
  box-shadow: 3px 3px 25px  1px rgba(255,255,255,1) ;
`;

const Karavan = styled.span`
  font-family: Helvetica;
  font-style: normal;
  font-weight: 400;
  color: rgba(255,255,255,1);
  font-size: 48px;
  margin-top: 85px;
  margin-left: 139px;
`;

const SignIn = styled.span`
  font-family: Helvetica;
  font-style: normal;
  font-weight: 400;
  color: rgba(255,255,255,1);
  font-size: 24px;
  margin-top: 34px;
  margin-left: 187px;
`;

const InputGroup = styled.div`
  width: 388px;
  height: 303px;
  flex-direction: column;
  justify-content: space-between;
  margin-top: 52px;
  margin-left: 31px;
  position: relative;
  display: flex;
`;

const Button = styled.div`
  top: 252px;
  left: 22px;
  width: 345px;
  height: 51px;
  position: absolute;
  opacity: 0;
  border: none;
`;

export default LogIn;
