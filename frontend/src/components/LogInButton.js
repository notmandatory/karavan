import React, { Component } from "react";
import styled, { css } from "styled-components";
import EntypoIcon from "react-native-vector-icons/dist/Entypo";

function LogInButton(props) {
  return (
    <Container {...props}>
      <Group>
        <Group2>
          <Button>Continue</Button>
          <EntypoIcon
            name="chevron-right"
            style={{
              color: "rgba(255,255,255,1)",
              fontSize: 27
            }}
          ></EntypoIcon>
        </Group2>
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
  border-radius: 10px;
  padding-left: 16px;
  padding-right: 16px;
`;

const Group = styled.div`
  flex-direction: row;
  width: 80px;
  height: 27px;
  display: flex;
`;

const Group2 = styled.div`
  width: 108px;
  height: 29px;
  flex-direction: row;
  justify-content: space-between;
  margin-left: -9px;
  display: flex;
`;

const Button = styled.span`
  font-family: Helvetica;
  color: #fff;
  font-size: 20px;
  font-weight: 700;
  text-align: center;
  font-style: normal;
  align-self: center;
`;

export default LogInButton;
