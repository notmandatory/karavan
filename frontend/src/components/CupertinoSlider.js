import React, { Component } from "react";
import styled, { css } from "styled-components";
import { Slider } from "material-ui-slider";

function CupertinoSlider(props) {
  return (
    <Container {...props}>
      <Slider defaultValue={50} style={{}} />
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  justify-content: center;
  background-color: transparent;
  flex-direction: column;
`;

export default CupertinoSlider;
