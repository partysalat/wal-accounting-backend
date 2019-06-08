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
import {
  faBeer,
  faCocktail,
  faCoffee,
  faGlassWhiskey,
} from '@fortawesome/free-solid-svg-icons';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import ListItemText from '@material-ui/core/ListItemText';
import './RevertDialog.css';
import { appendNews, loadNews, removeNews } from '../../../redux/actions';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';

class RevertDialog extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      offset: 0,
      isInfiniteLoading: false,
    };
    this.close = this.close.bind(this);
    this.onOpen = this.onOpen.bind(this);
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
  remove = (newsItem) => {
    console.log('Remove', newsItem);
    this.props.removeNews(newsItem.news.id);
  }
  getDrinkIcon(drinkType) {
    switch (drinkType) {
      case 'COCKTAIL': return faCocktail;
      case 'SOFTDRINK': return faCoffee;
      case 'SHOT': return faGlassWhiskey;
      case 'BEER': return faBeer;
    }
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
        <DialogTitle>Rückgängig</DialogTitle>
        <DialogContent className="dialog-content">
          <List dense >
            <Infinite
              elementHeight={54}
              containerHeight={300}
              infiniteLoadBeginEdgeOffset={200}
              onInfiniteLoad={this.handleInfiniteLoad}
              // loadingSpinnerDelegate={this.elementInfiniteLoad()}
              isInfiniteLoading={this.props.loading || this.props.lastLoadEmpty}
            >{this.props.news.map(newsItem => (
              <ListItem key={newsItem.news.id}>
                <ListItemIcon>
                  <FontAwesomeIcon icon={this.getDrinkIcon(newsItem.payload.DrinkPayload.type)} size="2x" style={{ width: '75px' }} />
                </ListItemIcon>
                <ListItemText
                  primary={`${newsItem.news.amount}x ${newsItem.payload.DrinkPayload.name}`}
                  secondary={`${newsItem.user.name}`}
                />
                <ListItemSecondaryAction>
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() => this.remove(newsItem)}
                  >X</Button>
                </ListItemSecondaryAction>
              </ListItem>))}

            </Infinite>


          </List>
        </DialogContent>
        <DialogActions>
          <Button variant="contained" color="primary" onClick={this.close} size="large">
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
  loadNews,
  appendNews,
  removeNews
  ,
};

export default connect(mapStateToProps, mapDispatchToProps)(RevertDialog);
