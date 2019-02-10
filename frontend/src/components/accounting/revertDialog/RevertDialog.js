import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import DialogActions from '@material-ui/core/DialogActions';
import { connect } from 'react-redux';
import List from '@material-ui/core/List';
import './RevertDialog.css';
import { loadNews } from '../../../redux/actions';

class RevertDialog extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    };
    this.close = this.close.bind(this);
    this.submit = this.submit.bind(this);
    this.onOpen = this.onOpen.bind(this);
  }
  componentWillMount() {

  }

  submit() {

  }
  close() {
    this.props.onClose();
  }
  onOpen() {
    this.props.loadNews(0);
  }
  render() {
    return (
      <Dialog
        fullWidth
        maxWidth="xl"
        scroll="paper"
        open={this.props.open}
        onClose={this.close}
        onEnter={this.onOpen}
      >
        <DialogTitle>Rückgängig</DialogTitle>
        <DialogContent className="dialog-content">
          <List dense />
        </DialogContent>
        <DialogActions>
          <Button color="primary" onClick={this.close}>
            Abbrechen
          </Button>
          <Button variant="contained" color="primary" onClick={this.submit}>
            Ok
          </Button>
        </DialogActions>
      </Dialog>
    );
  }
}

function mapStateToProps(state, { drinkType }) {
  return {
    news: state.news,
  };
}
const mapDispatchToProps = {
  loadNews,
};

export default connect(mapStateToProps, mapDispatchToProps)(RevertDialog);
