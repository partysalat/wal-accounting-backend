import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import DialogActions from '@material-ui/core/DialogActions';
import { connect } from 'react-redux';
import Grid from '@material-ui/core/Grid';
import ButtonBase from "@material-ui/core/ButtonBase";
import './DrinkDialog.css';
import { loadDrinks, loadUser } from '../../../redux/actions';

class DrinkDialog extends React.Component {
  componentDidMount() {
    this.props.loadUser();
    this.props.loadDrinks(this.props.drinkType);
  }

  render() {
    return (
      <Dialog
        fullWidth
        maxWidth="xl"
        scroll="paper"
        open={this.props.open}
        onClose={this.props.onClose}
        aria-labelledby="max-width-dialog-title"
      >
        <DialogTitle id="max-width-dialog-title">Neue Bestellung von {this.props.drinkType}</DialogTitle>
        <DialogContent>
          <Grid container spacing={8}>

            {this.props.drinks.map(drink => (
              <Grid xs={3} item key={drink.id}>
                <ButtonBase className="dialog-buttons"> {drink.name}</ButtonBase>
              </Grid>
          ))}

          </Grid>
        </DialogContent>
        <DialogActions>
          <Button color="primary" onClick={this.props.onClose}>
            Close
          </Button>
        </DialogActions>
      </Dialog>
    );
  }
}

function mapStateToProps(state, { drinkType }) {
  return {
    users: state.users,
    drinks: state.drinks[drinkType] || [],
  };
}
const mapDispatchToProps = {
  loadUser,
  loadDrinks,
};

export default connect(mapStateToProps, mapDispatchToProps)(DrinkDialog);
