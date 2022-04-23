import React, { Component } from "react";
import styled, { css } from "styled-components";

function DescriptorInputBox(props) {
  return (
    <Container {...props}>
      <InputStyle
        placeholder="Descriptor"
        maxLength={2000}
        autoFocus={false}
        disableFullscreenUI={false}
      ></InputStyle>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  background-color: transparent;
  flex-direction: row;
  align-items: center;
  border-width: 1px;
  border-color: rgba(255,255,255,1);
  border-bottom-width: 1px;
  border-style: solid;
`;

const InputStyle = styled.input`
  font-family: Helvetica;
  color: rgba(255,255,255,1);
  padding-right: 5px;
  font-size: 16px;
  align-self: stretch;
  flex: 1 1 0%;
  line-height: 16px;
  padding-top: 16px;
  padding-bottom: 8px;
  font-style: normal;
  font-weight: 400;
  border: none;
  background: transparent;
  display: flex;
  flex-direction: column;
`;

export default DescriptorInputBox;
