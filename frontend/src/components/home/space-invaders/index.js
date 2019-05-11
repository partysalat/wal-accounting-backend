import React, { Component } from 'react';
import { Button } from '@material-ui/core';
import './spaceInvaders.css';
import EndGameDialog from './endGameDialog';

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
      dialogVisible: false,
    };
    this.toggleFullscreen = this.toggleFullscreen.bind(this);
    this.maximizeFS = this.maximizeFS.bind(this);
    this.minimizeFS = this.minimizeFS.bind(this);
    this.closeDialog = this.closeDialog.bind(this);
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
    this.setState(oldState => (oldState.fullScreen ? this.minimizeFS() : this.maximizeFS()));
  }
  componentWillUnmount() {
  }
  showUserDialog(score) {
    this.setState({ dialogVisible: true, fullscreen: false, score });
  }

  componentDidMount() {
    // start();
    document.body.addEventListener('click', () => {
      this.setState(this.minimizeFS());
    }, false);

    document.body.addEventListener(FINISHED_EVENT_NAME, (e) => {
      this.showUserDialog(e.detail.score);
    }, false);
    window.startSpaceInvaders();
  }
  closeDialog() {
    this.setState({ dialogVisible: false, fullscreen: false, score: null });
  }
  render() {
    return (
      <div className={this.state.fullscreen ? 'fullscreenContainer space-invaders-container' : 'space-invaders-container'}>
        <canvas onClick={stopPropagation} id="game-canvas" width="640" height="640" className={this.state.fullscreen ? 'fullscreen' : ''} />
        <Button onClick={this.toggleFullscreen} className="fullscreenBtn">Fullscreen</Button>
        <EndGameDialog open={this.state.dialogVisible} onClose={this.closeDialog} score={this.state.score}/>
      </div>);
  }
}
