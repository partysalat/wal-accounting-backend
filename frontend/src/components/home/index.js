import React from 'react';
import Grid from '@material-ui/core/Grid';

import Bestlist from './bestlist';
import Feed from './feed';
import './home.css';

export default function Accounting() {
  return (
    <div>
      <Grid container spacing={16}>
        <Grid item md={9} sm={12}><Bestlist /></Grid>
        <Grid item md={3} sm={12}>
          <img src="/images/logo.png" className="logo" />
          <Feed />
        </Grid>
      </Grid>

    </div>
  );
}
