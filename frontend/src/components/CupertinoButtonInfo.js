import React, { Component } from "react";
import styled, { css } from "styled-components";
import EntypoIcon from "react-native-vector-icons/dist/Entypo";

function CupertinoButtonInfo(props) {
  return (
    <Container {...props}>
      <Group>
        <Button>Continue</Button>
        <EntypoIcon
          name="chevron-right"
          style={{
            top: 0,
            left: 61,
            position: "absolute",
            color: "rgba(128,128,128,1)",
            fontSize: 21
          }}
        ></EntypoIcon>
      </Group>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  background-color: #007AFF;
  justify-content: center;
  align-items: center;
  flex-direction: row;
  border-radius: 5px;
  padding-left: 16px;
  padding-right: 16px;
`;

const Group = styled.div`
  flex-direction: row;
  width: 68px;
  height: 20px;
  position: relative;
  display: flex;
`;

const Button = styled.span`
  font-family: Roboto;
  color: #fff;
  font-size: 32px;
  font-weight: 500;
`;

export default CupertinoButtonInfo;
