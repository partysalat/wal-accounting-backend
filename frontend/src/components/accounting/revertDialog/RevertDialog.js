import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import DialogActions from '@material-ui/core/DialogActions';
import { connect } from 'react-redux';
import List from '@material-ui/core/List';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItem from '@material-ui/core/ListItem';
import Infinite from 'react-infinite';
import { faCocktail } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import ListItemText from '@material-ui/core/ListItemText';
import './RevertDialog.css';
import { appendNews, loadNews } from '../../../redux/actions';

class RevertDialog extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      offset: 0,
      isInfiniteLoading: false,
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
    this.setState({
      offset: 0,
    });
  }
  handleInfiniteLoad = () => {
    console.log('On infinite load');
    this.props.appendNews(this.state.offset + 20);
    this.setState(({ offset }) => ({
      offset: offset + 20,
      isInfiniteLoading: true,
    }));
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
          <List dense >
            <Infinite
              elementHeight={40}
              containerHeight={250}
              infiniteLoadBeginEdgeOffset={200}
              onInfiniteLoad={this.handleInfiniteLoad}
              // loadingSpinnerDelegate={this.elementInfiniteLoad()}
              isInfiniteLoading={this.state.isInfiniteLoading}
            >{this.props.news.map(newsItem => (
              <ListItem key={newsItem.news.id}>
                <ListItemIcon>
                  <FontAwesomeIcon icon={faCocktail} size="2x" />
                </ListItemIcon>
                <ListItemText
                  primary="Single-line item"
                  secondary="Secondary text"
                />
              </ListItem>))}

            </Infinite>;


          </List>
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

function mapStateToProps(state) {
  return {
    news: state.news || [],
  };
}
const mapDispatchToProps = {
  loadNews,
  appendNews
  ,
};

export default connect(mapStateToProps, mapDispatchToProps)(RevertDialog);
