import React, { Component } from "react";
import styled, { css } from "styled-components";

function DescriptorTextbox(props) {
  return (
    <Container {...props}>
      <InputStyle placeholder="Network"></InputStyle>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  border-bottom-width: 1px;
  border-color: #D9D5DC;
  background-color: transparent;
  flex-direction: row;
  align-items: center;
`;

const InputStyle = styled.input`
  font-family: Roboto;
  color: #000;
  padding-right: 5px;
  font-size: 28px;
  align-self: stretch;
  flex: 1 1 0%;
  line-height: 16px;
  padding-top: 16px;
  padding-bottom: 8px;
  border: none;
  background: transparent;
  display: flex;
  flex-direction: column;
`;

export default DescriptorTextbox;
