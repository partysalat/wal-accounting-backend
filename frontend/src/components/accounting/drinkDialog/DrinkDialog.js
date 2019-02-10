import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import DialogActions from '@material-ui/core/DialogActions';
import { connect } from 'react-redux';
import Grid from '@material-ui/core/Grid';
import ButtonBase from '@material-ui/core/ButtonBase';
import './DrinkDialog.css';
import { loadDrinks, loadUser } from '../../../redux/actions';

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
    this.close = this.close.bind(this);
  }
  componentDidMount() {
    this.props.loadUser();
    this.props.loadDrinks(this.props.drinkType);
  }
  goToUserPage(drink) {
    this.setState({ page: PAGES.USERS, selectedDrink: drink });
  }
  reset() {
    this.setState({ page: PAGES.DRINKS, selectedDrink: null });
  }
  isSelected(user) {
    return this.state.selectedUsers.indexOf(user) > -1;
  }
  selectUser(user) {
    this.setState(prevState => ({
      ...prevState,
      selectedUsers: this.isSelected(user) ?
        prevState.selectedUsers.filter(item => item !== user) :
        [...prevState.selectedUsers, user],
    }));
  }
  close() {
    this.props.onClose();
    setTimeout(() => this.reset(), 1000);
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
          {this.props.users.map(user => (
            <Grid xs={3} item key={user.id}>
              <ButtonBase className={`dialog-buttons ${this.isSelected(user) && 'active'}`} onClick={() => this.selectUser(user)}> {user.name}</ButtonBase>
            </Grid>
          ))}

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
        <DialogTitle id="max-width-dialog-title">Neue Bestellung von {this.props.drinkType}</DialogTitle>
        <DialogContent>{pageContent}</DialogContent>
        <DialogActions>
          <Button color="primary" onClick={this.close}>
            Abbrechen
          </Button>
          {this.state.page === PAGES.USERS && <Button color="primary" onClick={this.close}>
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
};

export default connect(mapStateToProps, mapDispatchToProps)(DrinkDialog);
