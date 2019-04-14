import React, { Component } from 'react';
import './spaceInvaders.css';
// import { start } from './spaceInvaders';

export default class SpaceInvaders extends Component {
  componentDidMount() {
    // start();
    window.startSpaceInvaders();
  }
  render() {
    return <canvas id="game-canvas" width="640" height="640" />;
  }
}
