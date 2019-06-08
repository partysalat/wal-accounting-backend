import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import DialogActions from '@material-ui/core/DialogActions';
import TextField from '@material-ui/core/TextField';
import './AddUserDialog.css';
import { connect } from 'react-redux';
import { addUser, loadNews } from '../../../redux/actions';

class AddUserDialog extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      drinker: '',
    };
    this.close = this.close.bind(this);
    this.onOpen = this.onOpen.bind(this);
  }

  close() {
    this.setState({
      drinker: '',
    });
    this.props.onClose();
  }
  handleChange =(event) => {
    this.setState({
      drinker: event.target.value,
    });
  }
  onOpen() {
    // this.props.loadNews(0);
  }
  onSubmit = () => {
    this.props.addUser(this.state.drinker);
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
        <DialogTitle>Neuer Trinker</DialogTitle>
        <DialogContent className="dialog-content">
          <form noValidate autoComplete="off">
            <TextField
              id="standard-name"
              label="Name"
              value={this.state.drinker}
              onChange={this.handleChange}
              margin="normal"
            />
          </form>
        </DialogContent>
        <DialogActions>
          <Button onClick={this.close} variant="raised" size="large">
            Abbrechen
          </Button>
          <Button variant="contained" size="large" color="primary" onClick={this.onSubmit}>
            Ok
          </Button>
        </DialogActions>
      </Dialog>
    );
  }
}

const mapDispatchToProps = {
  addUser,
};

export default connect(null, mapDispatchToProps)(AddUserDialog);
