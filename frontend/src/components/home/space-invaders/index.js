import React, { Component } from 'react';
import './spaceInvaders.css';
// import { start } from './spaceInvaders';

const FINISHED_EVENT_NAME = 'space-invaders-finished';
export default class SpaceInvaders extends Component {
  componentDidMount() {
    // start();
    document.body.addEventListener(FINISHED_EVENT_NAME, function () {
      console.log(arguments);
    }, false);
    window.startSpaceInvaders();
  }
  render() {
    return <canvas id="game-canvas" width="640" height="640" />;
  }
}
