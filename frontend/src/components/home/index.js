import React from 'react';
import Grid from '@material-ui/core/Grid';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';

import Bestlist from './bestlist';
import Feed from './feed';
import './home.css';

export default function Accounting() {
  return (
    <div>
      <Grid container spacing={16}>
        {/*<Grid item md={12} sm={12}>*/}
          {/*<AppBar position="static" color="default">*/}
            {/*<Toolbar>*/}
              {/*<Typography variant="h6" color="inherit">*/}
                {/*Kosmo B.R.A.*/}
              {/*</Typography>*/}
            {/*</Toolbar>*/}
          {/*</AppBar>*/}
        {/*</Grid>*/}
        <Grid item md={2} sm={12}>
          <img src="/images/logo.png" className="logo" />
        </Grid>
        <Grid item md={6} sm={12}><Bestlist /></Grid>
        <Grid item md={4} sm={12}><Feed /></Grid>
      </Grid>

    </div>
  );
}
