import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogActions from '@material-ui/core/DialogActions';
import { connect } from 'react-redux';
import { loadDrinks, loadUser } from '../../../redux/actions';

class DrinkDialog extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      open: false,
    };
  }
  componentDidMount() {
    this.props.loadUser();
    this.props.loadDrinks(this.props.drinkType);
  }

  render() {
    return (
      <Dialog
        fullWidth
        maxWidth="xl"
        open={this.state.open}
        // onClose={this.handleClose}
        aria-labelledby="max-width-dialog-title"
      >
        <DialogTitle id="max-width-dialog-title">Optional sizes</DialogTitle>
        <DialogContent>
          <div>test</div>
          <DialogContentText />

        </DialogContent>
        <DialogActions>
          <Button color="primary">
            Close
          </Button>
        </DialogActions>
      </Dialog>
    );
  }
}

function mapStateToProps(state) {
  return {
    users: state.users,
    drinks: state.drinks,
  };
}
const mapDispatchToProps = {
  loadUser,
  loadDrinks,
};

export default connect(mapStateToProps, mapDispatchToProps)(DrinkDialog);
