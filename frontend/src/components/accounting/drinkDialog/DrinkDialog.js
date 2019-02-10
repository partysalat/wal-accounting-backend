import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import DialogActions from '@material-ui/core/DialogActions';
import { connect } from 'react-redux';
import Grid from '@material-ui/core/Grid';
import ButtonBase from '@material-ui/core/ButtonBase';
import Slider from '@material-ui/lab/Slider';
import './DrinkDialog.css';
import { bookUserDrink, loadDrinks, loadUser } from '../../../redux/actions';

const PAGES = {
  DRINKS: 'DRINKS',
  USERS: 'USERS',
};

class DrinkDialog extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      page: PAGES.DRINKS,
      selectedUsers: [],
      selectedDrink: null,
    };
    this.goToUserPage = this.goToUserPage.bind(this);
    this.goToDrinkPage = this.goToDrinkPage.bind(this);
    this.close = this.close.bind(this);
    this.submit = this.submit.bind(this);
  }
  componentDidMount() {
    this.props.loadUser();
    this.props.loadDrinks(this.props.drinkType);
  }
  goToUserPage(drink) {
    this.setState({ page: PAGES.USERS, selectedDrink: drink });
  }
  goToDrinkPage() {
    this.setState({ page: PAGES.DRINKS });
  }
  reset() {
    this.setState({ page: PAGES.DRINKS, selectedDrink: null });
  }
  isSelected(userId) {
    return this.state.selectedUsers.find(selUser => selUser.userId === userId);
  }
  selectUser(userId) {
    this.setState(prevState => ({
      ...prevState,
      selectedUsers: this.isSelected(userId) ?
        prevState.selectedUsers.filter(item => item.userId !== userId) :
        [...prevState.selectedUsers, { userId, amount: 1 }],
    }));
  }
  close() {
    this.props.onClose();
    setTimeout(() => this.reset(), 1000);
  }
  submit() {
    const { state } = this;
    this.props.bookUserDrink(state.selectedDrink, state.selectedUsers);
    this.close();
  }
  handleSliderChange(userId, amount) {
    this.setState(prevState => ({
      selectedUsers: prevState.selectedUsers.map((user) => {
        if (user.userId !== userId) {
          return user;
        }
        return {
          ...user,
          amount,
        };
      }),
    }));
  }
  render() {
    let pageContent;
    if (this.state.page === PAGES.DRINKS) {
      pageContent = (
        <Grid container spacing={8}>
          {this.props.drinks.map(drink => (
            <Grid xs={3} item key={drink.id}>
              <ButtonBase className="dialog-buttons" onClick={() => this.goToUserPage(drink)}>{drink.name}</ButtonBase>
            </Grid>
        ))}

        </Grid>);
    } else {
      pageContent = (
        <Grid container spacing={8}>
          {this.props.users.map((user) => {
            const selectedUser = this.isSelected(user.id);
            return (
              <Grid xs={3} item key={user.id}>
                <ButtonBase
                  className={`dialog-buttons ${selectedUser && 'active'}`}
                  onClick={() => this.selectUser(user.id)}
                > {user.name} {selectedUser ? `(${selectedUser.amount})` : ''}
                </ButtonBase>
                {selectedUser && (<Slider
                  className="dialog-slider"
                  value={selectedUser.amount}
                  min={1}
                  max={20}
                  step={1}
                  onChange={(event, amount) => this.handleSliderChange(user.id, amount)}
                />)}
              </Grid>);
          })}

        </Grid>
      );
    }
    return (
      <Dialog
        fullWidth
        maxWidth="xl"
        scroll="paper"
        open={this.props.open}
        onClose={this.close}
        aria-labelledby="max-width-dialog-title"
      >
        <DialogTitle id="max-width-dialog-title">Neue Bestellung von {!this.state.selectedDrink && `${this.props.drinkType}`} {this.state.selectedDrink && `${this.state.selectedDrink.name}`}</DialogTitle>
        <DialogContent className="dialog-content">{pageContent}</DialogContent>
        <DialogActions>
          <Button color="primary" onClick={this.close}>
            Abbrechen
          </Button>
          {this.state.page === PAGES.USERS && <Button variant="contained" color="secondary" onClick={this.goToDrinkPage}>
            Zurück
          </Button>}
          {this.state.page === PAGES.USERS && <Button variant="contained" color="primary" onClick={this.submit}>
            Ok
          </Button>}
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
  bookUserDrink,
};

export default connect(mapStateToProps, mapDispatchToProps)(DrinkDialog);
