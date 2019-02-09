import React, { Component } from 'react';
import './App.css';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <Grid container spacing={24} className="grid-container">
            <Grid item xs={3}>
              <Button variant="contained" color="primary" className="full-height">
                Cocktails
              </Button>
            </Grid>
            <Grid item xs={3}>
              <Button variant="contained" color="secondary" className="full-height">
                Beer
              </Button>
            </Grid>
            <Grid item xs={3}>
              <Button variant="contained" className="full-height">
                Shots
              </Button>
            </Grid>
            <Grid item xs={3}>
              <Button variant="contained" color="primary" className="full-height">
                Softdrinks
              </Button>
            </Grid>
            <Grid item xs={3}>
              <Button variant="contained" color="primary" className="full-height">
                CTRL + Z
              </Button>
            </Grid>
            <Grid item xs={2}>
              <Button variant="contained" color="primary" className="full-height">
                Neuer Trinker
              </Button>
            </Grid>
            <Grid item xs={2}>
              <Button variant="contained" color="primary" className="full-height">
                Neuer Drink
              </Button>
            </Grid>
            <Grid item xs={2}>
              <Button variant="contained" color="primary" className="full-height">
                Sync Drinks
              </Button>
            </Grid>
            <Grid item xs={2}>
              <Button variant="contained" color="primary" className="full-height">
                Download Bestlist
              </Button>
            </Grid>

          </Grid>

        </header>
      </div>
    );
  }
}

export default App;
