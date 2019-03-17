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
import { toast, ToastContainer } from 'react-toastify';
import { connect } from 'react-redux';
import './Accounting.css';
import { AccountingButton } from './accountingButton/AccountingButton';
import AccountingButtonWithDrinkDialog from './accountingButton/AccountingButtonWithDrinkDialog';
import { syncAll } from '../../redux/actions';
import DrinkDialog from './drinkDialog/DrinkDialog';
import RevertDialog from './revertDialog/RevertDialog';
import AddUserDialog from './addUserDialog/AddUserDialog';
import AddDrinkDialog from './addDrinkDialog/AddDrinkDialog';

class Accounting extends Component {
  render() {
    return (
      <div className="App">
        <Grid container className="grid-container">
          <Grid item xs={3} >
            <AccountingButtonWithDrinkDialog icon={faCocktail} color="primary" drinkType="COCKTAIL" dialogComponent={DrinkDialog}>
                Cocktails
            </AccountingButtonWithDrinkDialog>
          </Grid>
          <Grid item xs={3}>
            <AccountingButtonWithDrinkDialog icon={faBeer} color="secondary" drinkType="BEER" dialogComponent={DrinkDialog}>
                Beer
            </AccountingButtonWithDrinkDialog>
          </Grid>
          <Grid item xs={3}>
            <AccountingButtonWithDrinkDialog icon={faGlassWhiskey} color="default" drinkType="SHOT" dialogComponent={DrinkDialog}>
                Shots
            </AccountingButtonWithDrinkDialog>
          </Grid>
          <Grid item xs={3}>
            <AccountingButtonWithDrinkDialog icon={faCoffee} color="primary" className="bnd-green" drinkType="SOFTDRINK" dialogComponent={DrinkDialog}>
                Softdrinks
            </AccountingButtonWithDrinkDialog>
          </Grid>
          <Grid item xs={3}>
            <AccountingButtonWithDrinkDialog icon={faUndo} color="default" className="bnd-yellow" dialogComponent={RevertDialog}>
                CTRL + Z
            </AccountingButtonWithDrinkDialog>
          </Grid>
          <Grid item xs={6} container spacing={24}>
            <Grid item xs={6}>
              <AccountingButtonWithDrinkDialog icon={faUserPlus} color="default" dialogComponent={AddUserDialog}>
                Neuer Trinker
              </AccountingButtonWithDrinkDialog>
            </Grid>
            <Grid item xs={6}>
              <AccountingButtonWithDrinkDialog icon={faCartPlus} color="default" dialogComponent={AddDrinkDialog}>
                Neuer Drink
              </AccountingButtonWithDrinkDialog>

            </Grid>
            <Grid item xs={6}>
              <AccountingButton icon={faSync} onClick={this.props.syncAll}>
                  Sync Drinks
              </AccountingButton>
            </Grid>
            <Grid item xs={6}>
              <AccountingButton icon={faSave} >
                <a href="/api/news/csv" target="_blank">Download Bestlist</a>
              </AccountingButton>
            </Grid>
          </Grid>

        </Grid>

        <ToastContainer position={toast.POSITION.BOTTOM_CENTER} hideProgressBar newestOnTop />
      </div>
    );
  }
}
export default connect(null, {
  syncAll,
})(Accounting);
