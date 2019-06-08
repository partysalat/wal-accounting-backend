import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import DialogActions from '@material-ui/core/DialogActions';
import TextField from '@material-ui/core/TextField';
import ButtonBase from '@material-ui/core/ButtonBase';
import Grid from '@material-ui/core/Grid';
import { connect } from 'react-redux';
import './AddDrinkDialog.css';
import { addDrink } from '../../../redux/actions';

const DRINKS = [
  'COCKTAIL',
  'BEER',
  'SHOT',
  'SOFTDRINK',
];
class AddDrinkDialog extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      drinkName: '',
      drinkType: null,
    };
    this.close = this.close.bind(this);
    this.onOpen = this.onOpen.bind(this);
  }

  close() {
    this.setState({
      drinkName: '',
      drinkType: null,
    });
    this.props.onClose();
  }
  handleChange =(event) => {
    this.setState({
      drinkName: event.target.value,
    });
  }
  onOpen() {
    // this.props.loadNews(0);
  }
  selectDrinkType = (drinkType) => {
    this.setState({
      drinkType,
    });
  }
  onSubmit = () => {
    this.props.addDrink(this.state.drinkName, this.state.drinkType);
    this.close();
  }
  render() {
    return (
      <Dialog
        fullWidth
        maxWidth="sm"
        scroll="paper"
        open={this.props.open}
        onClose={this.close}
        onEnter={this.onOpen}
      >
        <DialogTitle>Neuer Drink</DialogTitle>
        <DialogContent className="dialog-content">
          <Grid container>
            {DRINKS.map(drink =>
            (<Grid xs={3} item key={drink}>
              <ButtonBase
                className={`dialog-buttons ${this.state.drinkType === drink && 'active'}`}
                onClick={() => this.selectDrinkType(drink)}
              > {drink}
              </ButtonBase>
            </Grid>))}
          </Grid>
          <form noValidate autoComplete="off">
            <TextField
              id="standard-name"
              label="Name"
              value={this.state.drinkName}
              onChange={this.handleChange}
              margin="normal"
            />
          </form>
        </DialogContent>
        <DialogActions>
          <Button onClick={this.close} variant="raised" size="large">
            Abbrechen
          </Button>
          <Button size="large" variant="contained" color="primary" onClick={this.onSubmit}>
            Ok
          </Button>


        </DialogActions>
      </Dialog>
    );
  }
}

function mapStateToProps(state) {
  return {
    news: state.drinkNews.data || [],
    loading: state.drinkNews.loading || false,
    lastLoadEmpty: state.drinkNews.lastLoadEmpty,
  };
}
const mapDispatchToProps = {
  addDrink,
};

export default connect(mapStateToProps, mapDispatchToProps)(AddDrinkDialog);
