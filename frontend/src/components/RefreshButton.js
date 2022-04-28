import React, { Component } from "react";
import styled, { css } from "styled-components";
import MaterialCommunityIconsIcon from "react-native-vector-icons/dist/MaterialCommunityIcons";

function RefreshButton(props) {
  return (
    <Container {...props}>
      <MaterialCommunityIconsIcon
        name="refresh"
        style={{
          color: "#fff",
          fontSize: 24,
          alignSelf: "center"
        }}
      ></MaterialCommunityIconsIcon>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  background-color: rgba(0,122,255,1);
  align-items: center;
  justify-content: center;
  border-radius: 28px;
  min-width: 40px;
  min-height: 40px;
  flex-direction: column;
  box-shadow: 0px 2px 1.2px  0.05px #111 ;
`;

export default RefreshButton;
