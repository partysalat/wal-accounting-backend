import React, { Component } from 'react';
import './spaceInvaders.css';
import { Button } from '@material-ui/core';

const FINISHED_EVENT_NAME = 'space-invaders-finished';
function stopPropagation(e) {
  console.log(e);
  e.stopPropagation();
}

export default class SpaceInvaders extends Component {
  constructor(props) {
    super(props);
    this.state = {
      fullscreen: false,
    };
    this.toggleFullscreen = this.toggleFullscreen.bind(this);
    this.maximizeFS = this.maximizeFS.bind(this);
    this.minimizeFS = this.minimizeFS.bind(this);
  }
  minimizeFS() {
    window.resize(null);
    return { fullscreen: false };
  }
  maximizeFS() {
    window.resize(640);
    return { fullscreen: true };
  }
  toggleFullscreen() {
    this.setState((oldState) => {
      const v = oldState.fullScreen ? this.minimizeFS() : this.maximizeFS();
      console.log(v);
      return v;
    });
  }
  componentDidMount() {
    // start();
    document.body.addEventListener('click', () => {
      this.setState(this.minimizeFS());
    }, false);

    document.body.addEventListener(FINISHED_EVENT_NAME, function () {
      console.log(arguments);
    }, false);
    window.startSpaceInvaders();
  }
  render() {
    return (
      <div className={this.state.fullscreen ? 'fullscreenContainer space-invaders-container' : 'space-invaders-container'}>
        <canvas onClick={stopPropagation} id="game-canvas" width="640" height="640" className={this.state.fullscreen ? 'fullscreen' : ''} />
        <Button onClick={this.toggleFullscreen} className="fullscreenBtn">Fullscreen</Button>
      </div>);
  }
}
