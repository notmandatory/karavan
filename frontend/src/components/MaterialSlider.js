import React, { Component } from "react";
import styled, { css } from "styled-components";
import { Slider } from "material-ui-slider";

function MaterialSlider(props) {
  return (
    <Container {...props}>
      <Slider
        defaultValue={50}
        style={{
          height: 30,
          width: 150
        }}
      />
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  background-color: transparent;
  justify-content: center;
  flex-direction: column;
`;

export default MaterialSlider;
