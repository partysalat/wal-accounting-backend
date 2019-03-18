import React from 'react';
import Grid from '@material-ui/core/Grid';
import Bestlist from './bestlist';
import Feed from './feed';
import './home.css';

export default function Accounting() {
  return (
    <div>
      <Grid container>
        <Grid item xs={8}><Bestlist /></Grid>
        <Grid item xs={4}><Feed /></Grid>
      </Grid>

    </div>
  );
}
