import React, { Component } from "react";
import styled, { css } from "styled-components";

function MaterialStackedLabelTextbox1(props) {
  return (
    <Container {...props}>
      <SendToAddress>Send to Address</SendToAddress>
      <InputStyle placeholder="address"></InputStyle>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  border-bottom-width: 1px;
  border-color: #D9D5DC;
  background-color: transparent;
  flex-direction: column;
`;

const SendToAddress = styled.span`
  font-family: Roboto;
  font-size: 12px;
  text-align: left;
  color: #000;
  opacity: 0.6;
  padding-top: 16px;
`;

const InputStyle = styled.input`
  font-family: Roboto;
  color: #000;
  font-size: 16px;
  align-self: stretch;
  flex: 1 1 0%;
  line-height: 16px;
  padding-top: 8px;
  padding-bottom: 8px;
  border: none;
  background: transparent;
  display: flex;
  flex-direction: column;
`;

export default MaterialStackedLabelTextbox1;
