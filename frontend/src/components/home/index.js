import React from 'react';
import Grid from '@material-ui/core/Grid';

import Bestlist from './bestlist';
import Feed from './feed';
import SpaceInvaders from './space-invaders';
import './home.css';

export default function Accounting() {
  return (
    <div>
      <Grid container spacing={16}>
        <Grid item xs={6}><Bestlist /></Grid>
        <Grid item xs={6}>
          <Grid container spacing={8}>
            <Grid xs={6} item className="feed-container"><Feed /></Grid>
            <Grid xs={6} item className="space-invaders-container">
              <img src="/images/logo.png" className="logo" />
              <SpaceInvaders />
            </Grid>

          </Grid>
        </Grid>
      </Grid>

    </div>
  );
}
