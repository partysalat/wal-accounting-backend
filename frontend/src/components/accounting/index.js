import React, { Component } from 'react';

import Grid from '@material-ui/core/Grid';
import {
  faBeer,
  faCartPlus,
  faCocktail,
  faCoffee,
  faGlassWhiskey,
  faSave,
  faSync,
  faUndo,
  faUserPlus,
} from '@fortawesome/free-solid-svg-icons';
import './Accounting.css';
import { AccountingButton } from './accountingButton/AccountingButton';
import DrinkDialog from './drinkDialog/DrinkDialog';


class Accounting extends Component {
  render() {
    return (
      <div className="App">
        <div className="App-header">
          <Grid container spacing={24} className="grid-container">
            <Grid item xs={3}>
              <AccountingButton icon={faCocktail} color="primary">
                Cocktails
                <DrinkDialog drinkType="COCKTAIL" />
              </AccountingButton>
            </Grid>
            <Grid item xs={3}>
              <AccountingButton icon={faBeer} color="secondary">
                Beer
                <DrinkDialog drinkType="BEER" />
              </AccountingButton>
            </Grid>
            <Grid item xs={3}>
              <AccountingButton icon={faGlassWhiskey} color="default">
                Shots
                <DrinkDialog drinkType="SHOT" />
              </AccountingButton>
            </Grid>
            <Grid item xs={3}>
              <AccountingButton icon={faCoffee} color="primary" className="bnd-green">
                Softdrinks
                <DrinkDialog drinkType="SOFTDRINK" />
              </AccountingButton>
            </Grid>
            <Grid item xs={3}>
              <AccountingButton icon={faUndo} color="default" className="bnd-yellow">
                CTRL + Z
              </AccountingButton>
            </Grid>
            <Grid item xs={6} container spacing={24}>
              <Grid item xs={6}>
                <AccountingButton icon={faUserPlus}>
                  Neuer Trinker
                </AccountingButton>
              </Grid>
              <Grid item xs={6}>
                <AccountingButton icon={faCartPlus}>
                  Neuer Drink
                </AccountingButton>
              </Grid>
              <Grid item xs={6}>
                <AccountingButton icon={faSync}>
                  Sync Drinks
                </AccountingButton>
              </Grid>
              <Grid item xs={6}>
                <AccountingButton icon={faSave}>
                  Download Bestlist
                </AccountingButton>
              </Grid>
            </Grid>

          </Grid>

        </div>
      </div>
    );
  }
}

export default Accounting;
