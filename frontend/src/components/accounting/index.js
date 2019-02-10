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
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';
import './Accounting.css';
import { AccountingButton } from './accountingButton/AccountingButton';
import AccountingButtonWithDrinkDialog from './accountingButton/AccountingButtonWithDrinkDialog';
import { connect } from 'react-redux';
import { syncAll } from '../../redux/actions';

class Accounting extends Component {
  render() {
    return (
      <div className="App">
        <div className="App-header">
          <Grid container spacing={16} className="grid-container">
            <Grid item xs={3} >
              <AccountingButtonWithDrinkDialog icon={faCocktail} color="primary" drinkType="COCKTAIL">
                Cocktails
              </AccountingButtonWithDrinkDialog>
            </Grid>
            <Grid item xs={3}>
              <AccountingButtonWithDrinkDialog icon={faBeer} color="secondary" drinkType="BEER">
                Beer
              </AccountingButtonWithDrinkDialog>
            </Grid>
            <Grid item xs={3}>
              <AccountingButtonWithDrinkDialog icon={faGlassWhiskey} color="default" drinkType="SHOT">
                Shots
              </AccountingButtonWithDrinkDialog>
            </Grid>
            <Grid item xs={3}>
              <AccountingButtonWithDrinkDialog icon={faCoffee} color="primary" className="bnd-green" drinkType="SOFTDRINK">
                Softdrinks
              </AccountingButtonWithDrinkDialog>
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
                <AccountingButton icon={faSync} onClick={this.props.syncAll}>
                  Sync Drinks
                </AccountingButton>
              </Grid>
              <Grid item xs={6}>
                <AccountingButton icon={faSave} >
                  Download Bestlist
                </AccountingButton>
              </Grid>
            </Grid>

          </Grid>

        </div>
        <ToastContainer position={toast.POSITION.BOTTOM_CENTER} hideProgressBar newestOnTop />
      </div>
    );
  }
}
export default connect(null, {
  syncAll,
})(Accounting);
