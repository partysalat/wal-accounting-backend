import React from 'react';
import Grid from '@material-ui/core/Grid';

import Bestlist from './bestlist';
import Feed from './feed';
import SpaceInvaders from './space-invaders';
import RandomFacts from './randomFacts';
import './home.css';

export default function Accounting() {
  return (
    <div>
      <Grid container spacing={16}>
        <Grid item xs={6}><Bestlist /></Grid>
        <Grid item xs={3}> <Feed /> </Grid>
        <Grid item xs={3}>
          <Grid container spacing={16}>
            <Grid item><img src="/images/logo.png" className="logo" /></Grid>
          </Grid>

          <Grid container className="space-invaders-container" >
            <Grid item><SpaceInvaders /></Grid>
          </Grid>
          <Grid container className="space-invaders-container">
            <Grid item><RandomFacts /></Grid>
          </Grid>
        </Grid>
      </Grid>

    </div>
  );
}
