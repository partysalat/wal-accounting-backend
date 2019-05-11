import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import DialogActions from '@material-ui/core/DialogActions';
import { connect } from 'react-redux';
import Grid from '@material-ui/core/Grid';
import ButtonBase from '@material-ui/core/ButtonBase';
import { loadUser, sendSpaceInvadersScore } from '../../../redux/actions';


class EndGameDialog extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedUser: null,
    };
    this.close = this.close.bind(this);
    this.submit = this.submit.bind(this);
  }
  componentDidMount() {
    this.props.loadUser();
  }
  reset() {
    this.setState({ selectedUser: null });
  }
  isSelected(userId) {
    return this.state.selectedUser && this.state.selectedUser.userId === userId;
  }
  selectUser(userId) {
    this.setState(prevState => ({
      ...prevState,
      selectedUser: this.isSelected(userId) ?
        null :
        { userId, amount: 1 },
    }));
  }
  close() {
    this.props.onClose();
    setTimeout(() => this.reset(), 1000);
  }
  submit() {
    const { state } = this;
    this.props.sendSpaceInvadersScore(state.selectedUser.userId, this.props.score);
    // alert(`${JSON.stringify(state)} ${this.props.score}`);
    // this.props.bookUserDrink(state.selectedDrink, state.selectedUsers);
    this.close();
  }
  render() {
    const pageContent = (
      <Grid container spacing={8}>
        {this.props.users.map((user) => {
            const selectedUser = this.isSelected(user.id);
            return (
              <Grid xs={3} item key={user.id}>
                <ButtonBase
                  className={`dialog-buttons ${selectedUser && 'active'}`}
                  onClick={() => this.selectUser(user.id)}
                > {user.name}
                </ButtonBase>
              </Grid>);
          })}

      </Grid>
    );

    return (
      <Dialog
        fullWidth
        maxWidth="xl"
        scroll="paper"
        open={this.props.open}
        onClose={this.close}
        aria-labelledby="max-width-dialog-title"
      >
        <DialogTitle id="max-width-dialog-title">Spielername</DialogTitle>
        <DialogContent className="dialog-content">{pageContent}</DialogContent>
        <DialogActions>
          {<Button variant="contained" color="primary" onClick={this.submit}>
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
  };
}
const mapDispatchToProps = {
  loadUser,
  sendSpaceInvadersScore,
};

export default connect(mapStateToProps, mapDispatchToProps)(EndGameDialog);
