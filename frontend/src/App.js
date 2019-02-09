import React, { Component } from 'react';
import './App.css';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faCoffee, faCocktail,faBeer, faGlassWhiskey, faUndo, faUserPlus,faCartPlus, faSync ,faSave} from '@fortawesome/free-solid-svg-icons'
import {AccountingButton} from "./components/AccountingButton";



class App extends Component {
  render() {
    return (
      <div className="App">
        <FontAwesomeIcon icon="coffee" />
        <header className="App-header">
          <Grid container spacing={24} className="grid-container">
            <Grid item xs={3}>
              <AccountingButton icon={faCocktail} color="primary">
                Cocktails
              </AccountingButton>
            </Grid>
            <Grid item xs={3}>
              <AccountingButton icon={faBeer} color="secondary">
                Beer
              </AccountingButton>
            </Grid>
            <Grid item xs={3}>
              <AccountingButton icon={faGlassWhiskey}>
                Shots
              </AccountingButton>
            </Grid>
            <Grid item xs={3}>
              <AccountingButton icon={faCoffee}>
                Softdrinks
              </AccountingButton>
            </Grid>
            <Grid item xs={3}>
              <AccountingButton icon={faUndo}>
                CTRL + Z
              </AccountingButton>
            </Grid>
            <Grid item xs={2}>
              <AccountingButton icon={faUserPlus}>
                Neuer Trinker
              </AccountingButton>
            </Grid>
            <Grid item xs={2}>
              <AccountingButton icon={faCartPlus}>
                Neuer Drink
              </AccountingButton>
            </Grid>
            <Grid item xs={2}>
              <AccountingButton icon={faSync}>
                Sync Drinks
              </AccountingButton>
            </Grid>
            <Grid item xs={2}>
              <AccountingButton icon={faSave}>
                Download Bestlist
              </AccountingButton>
            </Grid>

          </Grid>

        </header>
      </div>
    );
  }
}

export default App;
