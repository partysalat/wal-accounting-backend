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
        <Grid item md={6}><Bestlist /></Grid>
        <Grid item md={6}>
          <Grid container spacing={8}>
            <Grid md={6} item className="feed-container"><Feed /></Grid>
            <Grid md={6} item className="space-invaders-container">
              <img src="/images/logo.png" className="logo" />
              <SpaceInvaders />
            </Grid>

          </Grid>
        </Grid>
      </Grid>

    </div>
  );
}
